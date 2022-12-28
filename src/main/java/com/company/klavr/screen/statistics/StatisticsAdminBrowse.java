package com.company.klavr.screen.statistics;

import com.company.klavr.entity.Exercise;
import com.company.klavr.entity.User;
import com.company.klavr.entity.UserProgress;
import io.jmix.charts.component.SerialChart;
import io.jmix.core.DataManager;
import io.jmix.core.Sort;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.data.TableItems;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import com.company.klavr.entity.Statistics;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@UiController("Statistics_Admin.browse")
@UiDescriptor("statisticsAdmin-browse.xml")
@LookupComponent("statisticsesTable")
public class StatisticsAdminBrowse extends StandardLookup<Statistics> {
    DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy\nhh:mm");
    @Autowired
    private GroupTable<Statistics> statisticsesTable;
    @Autowired
    private CollectionContainer<UserProgress> statisticsesAdminDc;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private EntityComboBox<User> userComboBox;
    @Autowired
    private EntityComboBox<Exercise> exerciseComboBox;
    @Autowired
    private SerialChart adminSerialChart;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private EntityComboBox<User> entityTableComboBox;
    @Autowired
    private CollectionContainer<Statistics> statisticsesDc;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        //Сразу показываем красиво
        exerciseComboBox.setValue(null);
        userComboBox.setValue(null);
        Collection<UserProgress> userProgressList = new ArrayList<>();
        //Просто данные.
        List<Statistics> statisticsList = dataManager.load(Statistics.class).all().sort(Sort.by("finishDate")).list();
        if (statisticsList.size() != 0) {
            for (Statistics stat : statisticsList) {
                UserProgress up = new UserProgress(stat.getStatistics_to_exercise() + "\n"
                        + formatter.format(stat.getFinishDate()), stat.getTimer());
                userProgressList.add(up);
            }
        }
        adminSerialChart.setCategoryField("exercise");
        statisticsesAdminDc.setItems(userProgressList);
    }

    @Subscribe("entityTableComboBox")
    public void onEntityTableComboBoxValueChange(HasValue.ValueChangeEvent<User> event) {

        List<Statistics> statistics = dataManager.load(Statistics.class).query(
                "select e from Statistics_ e order by e.finishDate ").sort(Sort.by("finishDate")).list();
        if (entityTableComboBox.getValue() != null) {
            statistics = statistics.stream().filter(
                    stat -> Objects.equals(stat.getStatistics_to_user().getId(),
                            entityTableComboBox.getValue().getId())
            ).collect(Collectors.toList());
            statisticsesDc.setItems(statistics);
        }
        statisticsesDc.setItems(statistics);
    }


    @Subscribe("resetChartButton")
    public void onResetChartButtonClick(Button.ClickEvent event) {
        exerciseComboBox.setValue(null);
        userComboBox.setValue(null);
        Collection<UserProgress> userProgressList = new ArrayList<>();
        //Просто данные.
        List<Statistics> statisticsList = dataManager.load(Statistics.class).all().sort(Sort.by("finishDate")).list();
        if (statisticsList.size() != 0) {
            for (Statistics stat : statisticsList) {
                UserProgress up = new UserProgress(stat.getStatistics_to_exercise() + "\n"
                        + formatter.format(stat.getFinishDate()), stat.getTimer());
                userProgressList.add(up);
            }
        }
        adminSerialChart.setCategoryField("exercise");
        statisticsesAdminDc.setItems(userProgressList);
    }

    @Subscribe("userComboBox")
    public void onUserComboBoxValueChange(HasValue.ValueChangeEvent<User> event) {
        if (userComboBox.getValue() == null) return;
        exerciseComboBox.setValue(null);
        Collection<UserProgress> userProgressList = new ArrayList<>();
        Map<String, Integer> userStates = new HashMap<>();
        Map<String, Integer> userCounter = new HashMap<>();
        //Считаем среднее за упражнения у пользователя.
        List<User> userList = dataManager.load(User.class).all().list();
        List<Statistics> statisticsList = dataManager.load(Statistics.class).all().sort(Sort.by("finishDate")).list();
        if (statisticsList.size() != 0 && userList.size() != 0) {
            int result = 0;
            int counter = 0;
            for (Statistics stat : statisticsList) {
                if (Objects.equals(stat.getStatistics_to_user().getUsername(), userComboBox.getValue().getUsername())) {
                    String currentExName = stat.getStatistics_to_exercise();
                    if (userStates.containsKey(currentExName)) {
                        userStates.put(currentExName, userStates.get(currentExName) + stat.getTimer());
                        userCounter.put(currentExName, userCounter.get(currentExName) + 1);
                    } else {
                        userStates.put(currentExName, stat.getTimer());
                        userCounter.put(currentExName, 1);
                    }
                }
            }

            for (String nameEx : userStates.keySet()){
                int temp = userStates.get(nameEx) / userCounter.get(nameEx);
                UserProgress up = new UserProgress(dataManager.load(Exercise.class).condition(PropertyCondition.equal("name",nameEx)).one().getName(), temp);
                userProgressList.add(up);
            }
        }
        //Меняем параметры графика
        adminSerialChart.setCategoryField("exercise");
        userComboBox.getValue();

        statisticsesAdminDc.setItems(userProgressList);
    }

    @Subscribe("exerciseComboBox")
    public void onExerciseComboBoxValueChange(HasValue.ValueChangeEvent<Exercise> event) {
        if (exerciseComboBox.getValue() == null) return;
        userComboBox.setValue(null);
        Collection<UserProgress> userProgressList = new ArrayList<>();
        Map<UUID, Integer> userStates = new HashMap<>();
        Map<UUID, Integer> userCounter = new HashMap<>();
        //Считаем среднее за пользователя у упражнения.
        List<Exercise> exercisesList = dataManager.load(Exercise.class).all().list();
        List<Statistics> statisticsList = dataManager.load(Statistics.class).all().sort(Sort.by("finishDate")).list();
        if (statisticsList.size() != 0 && exercisesList.size() != 0) {
            int result = 0;
            int counter = 0;
            for (Statistics stat : statisticsList) {
                if (Objects.equals(stat.getStatistics_to_exercise(), exerciseComboBox.getValue().getName())) {
                    UUID currentID = stat.getStatistics_to_user().getId();
                    if (userStates.containsKey(currentID)) {
                        userStates.put(currentID, userStates.get(currentID) + stat.getTimer());
                        userCounter.put(currentID, userCounter.get(currentID) + 1);
                    } else {
                        userStates.put(currentID, stat.getTimer());
                        userCounter.put(currentID, 1);
                    }
                }
            }

            for (UUID uuid : userStates.keySet()){
                int temp = userStates.get(uuid) / userCounter.get(uuid);
                UserProgress up = new UserProgress(dataManager.load(User.class).id(uuid).one().getUsername(), temp);
                userProgressList.add(up);
            }
        }
        //Меняем параметры графика
        adminSerialChart.setCategoryField("exercise");
        statisticsesAdminDc.setItems(userProgressList);
    }

}