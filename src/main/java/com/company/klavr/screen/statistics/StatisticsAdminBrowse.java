package com.company.klavr.screen.statistics;

import com.company.klavr.entity.Exercise;
import com.company.klavr.entity.User;
import com.company.klavr.entity.UserProgress;
import io.jmix.charts.component.SerialChart;
import io.jmix.core.DataManager;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import com.company.klavr.entity.Statistics;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@UiController("Statistics_Admin.browse")
@UiDescriptor("statisticsAdmin-browse.xml")
@LookupComponent("statisticsesTable")
public class StatisticsAdminBrowse extends StandardLookup<Statistics> {
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

    @Subscribe("userComboBox")
    public void onUserComboBoxValueChange(HasValue.ValueChangeEvent<User> event) {
        if (userComboBox.getValue() == null) return;
        exerciseComboBox.setValue(null);
        Collection<UserProgress> userProgressList = new ArrayList<>();
        Map<UUID, Integer> userStates = new HashMap<>();
        Map<UUID, Integer> userCounter = new HashMap<>();

        //Считаем среднее за упражнения у пользователя.
        List<User> userList = dataManager.load(User.class).all().list();
        List<Statistics> statisticsList = dataManager.load(Statistics.class).all().list();
        if (statisticsList.size() != 0 && userList.size() != 0) {
            int result = 0;
            int counter = 0;
            for (Statistics stat : statisticsList) {
                if (Objects.equals(stat.getStatistics_to_user().getUsername(), userComboBox.getValue().getUsername())) {
                    UUID currentID = stat.getStatistics_to_exercise().getId();
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
                UserProgress up = new UserProgress(dataManager.load(Exercise.class).id(uuid).one().getName(), temp);
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
        List<Statistics> statisticsList = dataManager.load(Statistics.class).all().list();
        if (statisticsList.size() != 0 && exercisesList.size() != 0) {
            int result = 0;
            int counter = 0;
            for (Statistics stat : statisticsList) {
                if (Objects.equals(stat.getStatistics_to_exercise().getName(), exerciseComboBox.getValue().getName())) {
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
        exerciseComboBox.getValue();

        statisticsesAdminDc.setItems(userProgressList);
    }

}