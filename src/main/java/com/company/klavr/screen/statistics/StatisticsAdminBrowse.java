package com.company.klavr.screen.statistics;

import com.company.klavr.entity.Exercise;
import com.company.klavr.entity.User;
import io.jmix.charts.component.Chart;
import io.jmix.charts.component.SerialChart;
import io.jmix.core.DataManager;
import io.jmix.core.Sort;
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
    private CollectionContainer<Statistics> statisticsesAdminDc;
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
        System.out.println(userComboBox.getValue().toString());
        //Считаем среднее за упражнения у пользователя. Работает, но засунуть в график не можем
        List<Integer> results = new ArrayList<>();
        List<User> userList = dataManager.load(User.class).all().list();
        List<Statistics> statisticsList = dataManager.load(Statistics.class).all().list();
        if (statisticsList.size() != 0 && userList.size() != 0) {
            //for (User user : userList) {
                int result = 0;
                int counter = 0;
                for (Statistics stat : statisticsList) {
                    if (Objects.equals(stat.getStatistics_to_user().getUsername(), userComboBox.getValue().getUsername())) {
                        result += stat.getTimer();
                        counter += 1;
                    }
                }
                result = result / counter;
                results.add(result);
            //}
        }
        //Меняем параметры графика
        adminSerialChart.setCategoryField("statistics_to_user");
        userComboBox.getValue();

        Collection<Statistics> statisticsCollection = dataManager.load(Statistics.class).query(
                "select e from Statistics_ e where e.statistics_to_user = ?1 order by e.finishDate ", userComboBox.getValue()).sort(Sort.by("finishDate")).list();

        //dataManager возвращает только сущности
        /*statisticsCollection = dataManager.load(Statistics.class).query(
                "SELECT avg (e.timer) from Statistics_ e where e.statistics_to_exercise = ?1", exerciseComboBox.getValue()).list();*/
        statisticsesAdminDc.setItems(statisticsCollection);
    }

    @Subscribe("exerciseComboBox")
    public void onExerciseComboBoxValueChange(HasValue.ValueChangeEvent<Exercise> event) {
        exerciseComboBox.getValue();
        Collection<Statistics> statisticsCollection = dataManager.load(Statistics.class).query(
                "select e from Statistics_ e where e.statistics_to_exercise = ?1 order by e.finishDate ", exerciseComboBox.getValue()).sort(Sort.by("finishDate")).list();
        statisticsesAdminDc.setItems(statisticsCollection);
    }

}