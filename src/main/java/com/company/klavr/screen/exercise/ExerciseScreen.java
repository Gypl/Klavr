package com.company.klavr.screen.exercise;

import com.company.klavr.entity.Statistics;
import com.company.klavr.logic.ExerciseHandler;
import io.jmix.core.DataManager;
import io.jmix.ui.component.*;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("ExerciseScreen")
@UiDescriptor("Exercise-screen.xml")
public class ExerciseScreen extends Screen {

    private String written = "";
    private String needed = "";
    private int timerValue = 0;
    private boolean isTimer = false;
    private ExerciseHandler exerciseHandler;
    private boolean canExecution = true;
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

    public ExerciseScreen() {
        exerciseHandler = new ExerciseHandler(255, 10);
        needed = "тест очка";
    }

    @Subscribe
    public void onInit(InitEvent event) {
        needed = needed.replaceAll(" ", "\u00A0");
        text.setValue(needed);
        timeLabel.setValue(String.format(" %d сек", 0));
        lengthLabel.setValue(String.format(" 0/%d", exerciseHandler.getMaxLength()));
        mistakesLabel.setValue(String.format(" 0/%d", exerciseHandler.getMaxMistakes()));

        keyboardBox.setVisible(keyboardCheckBox.isChecked());
    }

    private void saveStatistics() {
        Statistics statistics = dataManager.create(Statistics.class);
        /*statistics.setStatistics_to_exercise(dataManager.load);
        statistics.setStatistics_to_user();

        statistics.setFinishDate();

        dataManager.save(statistics);*/
    }

    private void showKeyPress(char clicked) {
        keyboardBox.getComponents().forEach(
            component -> {
                if(component.toString().contains("Button")) {
                    Button button = (Button) component;
                    if (button.getCaption().trim().equals((clicked+"").toUpperCase())) {
                        System.out.println(clicked);
                    }

                }
            }
        );
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
        if(anton.getRawValue() == "") {
            return;
        }
        if (needed == "") {
            return;
        }
        if (!isTimer) {
            timer.start();
            this.isTimer = true;
        }

        if (!canExecution)
            return;

        handleInput();
    }

    private void exerciseDone() {
        System.out.println("Well done");
        executionStop();
    }

    private void executionStop() {
        timer.stop();
        canExecution = false;
        if (timerValue != 0) {
            exerciseHandler.setAverageSpeed(Math.round(exerciseHandler.getCurrentLength()/timerValue));
        }
    }

    private void wrongSymbol() {
        exerciseHandler.mistake();
        mistakesLabel.setValue(String.format(" %d/%d", exerciseHandler.getCurrentMistakes(), exerciseHandler.getMaxMistakes()));
        if (exerciseHandler.isMistakesFull()) {
            executionStop();
            System.err.println("You lose!");
        }
    }

    private void handleInput() {
        char inputSymbol = anton.getRawValue().charAt(0) == ' ' ? '\u00A0' : anton.getRawValue().charAt(0);
        showKeyPress(inputSymbol);
        anton.setValue("");
        if (correctSymbol(inputSymbol)) {
            updateUserInput();
            if (needed == "") {
                exerciseDone();
                return;
            }
        }
        else {
            wrongSymbol();
        }
    }

    private void updateUserInput() {
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
        messageDialog.setMessage(getFinalMessage());
        messageDialog.show();

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