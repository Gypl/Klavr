package com.company.klavr.screen.exercise;


import com.company.klavr.entity.Exercise;
import com.company.klavr.entity.Statistics;
import com.company.klavr.entity.User;
import com.company.klavr.logic.ExerciseHandler;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.*;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.yaml.snakeyaml.events.Event;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@UiController("ExerciseScreen")
@UiDescriptor("Exercise-screen.xml")
public class ExerciseScreen extends Screen {

    private String written = "";
    private String needed = "";
    private int timerValue = 0;
    private boolean isTimer = false;
    private ExerciseHandler exerciseHandler;
    private boolean canExecution = true;
    private double watchDogValue;
    private int keyPressTime;
    @Autowired
    private Label writtenText;
    @Autowired
    private Label text;
    @Autowired
    private TextField anton;
    @Autowired
    private CheckBox keyboardCheckBox;
    @Autowired
    private CssLayout keyboardBox;

    @Autowired
    private DataManager dataManager;
    @Autowired
    private Timer timer;
    @Autowired
    private Label lengthLabel;
    @Autowired
    private Label mistakesLabel;
    @Autowired
    private Label timeLabel;
    @Autowired
    private MessageDialogFacet messageDialog;
    @Autowired
    private Notifications notifications;

    private UUID id;
    private Exercise exercise;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private Timer watchDog;
    @Autowired
    private ProgressBar progressBar;

    public void setExerciseId(UUID id) {
        this.id = id;
    }

    private void initData() {
        needed = exercise.getText();
        exerciseHandler = new ExerciseHandler(needed.length(), exercise.getExercise_to_difficulty().getMistakesCount());

        written = "";
        writtenText.setValue("");

        needed = needed.replaceAll(" ", "\u00A0");
        text.setValue(needed);
        showKeyNeededToPress(needed.charAt(0));

        timeLabel.setValue(String.format(" %d сек", 0));
        lengthLabel.setValue(String.format(" 0/%d", exerciseHandler.getMaxLength()));
        mistakesLabel.setValue(String.format(" 0/%d", exerciseHandler.getMaxMistakes()));
        keyPressTime = exercise.getExercise_to_difficulty().getPressTime();

        keyboardBox.setVisible(keyboardCheckBox.isChecked());

        watchDogValue = keyPressTime;
        progressBar.setValue(0d);
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        initData();
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public ExerciseScreen() {
    }

    @Subscribe
    public void onInit(InitEvent event) {
    }

    private void saveStatistics() {
        Statistics statistics = dataManager.create(Statistics.class);
        //statistics.setStatistics_to_exercise(dataManager.load);
        String userName = currentAuthentication.getUser().getUsername().toString();

        UUID result = null;
        List<User> users = dataManager.load(User.class).all().list();
        if (users.size() != 0) {
            for (User user : users) {
                if (Objects.equals(user.getUsername(), userName))
                    result = user.getId();
            }
        }

        User currentUser = dataManager.load(User.class).id(result).one();


        /*User currentUser = dataManager.load(User.class)
                .condition(PropertyCondition.contains("USERNAME", userName))
                .one();
        currentUser = dataManager.load(User.class).condition(PropertyCondition.contains("ID", result)).one();*/
        statistics.setFinishDate(new Date());
        statistics.setStatistics_to_exercise(exercise);
        statistics.setStatistics_to_user(currentUser);
        statistics.setExerciseLength(exercise.getLength());
        statistics.setMistakesCount(exerciseHandler.getCurrentMistakes());
        statistics.setMaxMistakes(exercise.getExercise_to_difficulty().getMistakesCount());
        statistics.setTimer(timerValue);
        statistics.setSpeed(exerciseHandler.getAverageSpeed());
        dataManager.save(statistics);
    }

    private void showKeyNeededToPress(char neededToPress) {
        keyboardBox.getComponents().forEach(
            component -> {
                if(component.toString().contains("Button")) {
                    Button button = (Button) component;
                    this.setButtonDefaultStyle(button);

                    if (button.getCaption().trim().equals((neededToPress+"").toUpperCase())) {
                        makeButtonReadyToPress(button);
                    }
                }
            }
        );
    }

    private void setButtonDefaultStyle(Button button) {
        if (button.getId() != null) {
            if (button.getId().equals("buttonSpace")) {
                button.addStyleName("button-default-space");
            }
        }
        else {
            button.addStyleName("button-default-space");
        }
        button.removeStyleName("jmix-primary-action");
    }

    private void makeButtonReadyToPress(Button button) {
        button.addStyleName("jmix-primary-action");
    }

    @Subscribe("watchDog")
    public void onWatchDogTimerAction(Timer.TimerActionEvent event) {
        watchDogValue -= 0.1d;

        progressBar.setValue((keyPressTime - watchDogValue)/keyPressTime);

        if (Math.round(watchDogValue*10) == 0) {
            wrongSymbol();
            watchDogValue = exercise.getExercise_to_difficulty().getPressTime();

            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption("Время нажатия истекло").show();
        }
    }

    @Subscribe("keyboardCheckBox")
    public void onKeyboardCheckBoxValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        keyboardBox.setVisible(event.getValue());
    }

    @Subscribe("timer")
    public void onTimerTimerAction(Timer.TimerActionEvent event) {
        timeLabel.setValue(String.format(" %d сек", ++timerValue));
    }

    @Subscribe("anton")
    public void onAntonValueChange(HasValue.ValueChangeEvent event) {
        if(anton.getRawValue().equals("")) {
            return;
        }
        if (Objects.equals(needed, "")) {
            return;
        }
        if (!isTimer) {
            watchDog.start();
            timer.start();
            this.isTimer = true;
        }
        if (!canExecution)
            return;
        handleInput();
    }

    private void exerciseDone() {
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("Ура победа!").show();
        executionStop();
    }

    private void executionStop() {
        watchDog.stop();
        timer.stop();
        canExecution = false;
        isTimer = true;
        if (timerValue != 0) {
            exerciseHandler.setAverageSpeed(Math.round(exerciseHandler.getCurrentLength()/timerValue));
        }
        if (exerciseHandler.getCurrentLength() != 0) {
            messageDialog.setMessage(getFinalMessage());
            messageDialog.show();
            saveStatistics();
        }
    }

    private void reset() {
        timer.stop();
        watchDog.stop();
        timerValue = 0;
        isTimer = false;
        canExecution = true;
        initData();

        messageDialog.setMessage(getResetMessage());
        messageDialog.show();
    }

    private String getResetMessage() {
        return String.format("Перезапущено");
    }

    @Subscribe("resetButton")
    public void onResetButtonClick(Button.ClickEvent event) {
        reset();
    }

    private void wrongSymbol() {
        exerciseHandler.mistake();
        mistakesLabel.setValue(String.format(" %d/%d", exerciseHandler.getCurrentMistakes(), exerciseHandler.getMaxMistakes()));
        if (exerciseHandler.isMistakesFull()) {
            executionStop();
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption("Вы слабое звено").show();
        }
    }

    private void handleInput() {
        char inputSymbol = anton.getRawValue().charAt(0) == ' ' ? '\u00A0' : anton.getRawValue().charAt(0);
        anton.setValue("");
        if (correctSymbol(inputSymbol)) {
            watchDogValue = keyPressTime;
            updateUserInput();
            if (needed.isEmpty()) {
                exerciseDone();
                showKeyNeededToPress('N');
                return;
            }
            showKeyNeededToPress(needed.charAt(0));
        }
        else {
            wrongSymbol();
        }
    }

    private void updateUserInput() {
        if (written.length() > 13) {
            written = written.substring(1, written.length());
        }
        if (needed != "") {
            char changed = needed.charAt(0);
            needed = getText(needed);
            text.setValue(needed);
            written += changed == ' ' ? "\u00A0":changed;
            writtenText.setValue(written);

            exerciseHandler.incLength();
            lengthLabel.setValue(String.format(" %d/%d", exerciseHandler.getCurrentLength(), exerciseHandler.getMaxLength()));
        }
    }

    @Subscribe("completeButton")
    public void onCompleteButtonClick(Button.ClickEvent event) {
        executionStop();
    }

    private String getFinalMessage() {
        return String.format("Средняя скорость: %d симв/сек\n", exerciseHandler.getAverageSpeed()) +
                String.format("Количество ошибок: %d/%d\n", exerciseHandler.getCurrentMistakes(), exerciseHandler.getMaxMistakes()) +
                String.format("Время: %d c\n", timerValue);
    }

    private String getText(String old) {
        return old.substring(1, old.length());
    }

    private boolean correctSymbol(char inputSymbol) {
        return inputSymbol == needed.charAt(0);
    }

    @Subscribe("mainScreen")
    public void onMainScreenLayoutClick(LayoutClickNotifier.LayoutClickEvent event) {
        anton.focus();
    }
}