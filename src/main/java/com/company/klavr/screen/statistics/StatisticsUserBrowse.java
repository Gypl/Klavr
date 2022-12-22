package com.company.klavr.screen.statistics;

import com.company.klavr.entity.Exercise;
import com.company.klavr.entity.User;
import com.company.klavr.entity.UserProgress;
import io.jmix.charts.component.SerialChart;
import io.jmix.core.DataManager;
import io.jmix.core.Sort;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import com.company.klavr.entity.Statistics;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@UiController("Statistics_User.browse")
@UiDescriptor("statistics-user-browse.xml")
@LookupComponent("statisticsesTable")
public class StatisticsUserBrowse extends StandardLookup<Statistics> {
    DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy\nhh:mm");
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private CollectionContainer<UserProgress> statisticsesUserDc;
    @Autowired
    private SerialChart adminSerialChart;
    @Autowired
    private EntityComboBox<Exercise> exerciseComboBox;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private CollectionContainer<Statistics> statisticsesDc;

    @Subscribe("exerciseComboBox")
    public void onExerciseComboBoxValueChange(HasValue.ValueChangeEvent event) {

        if (exerciseComboBox.getValue() == null) {
            Collection<UserProgress> userProgressList = new ArrayList<>();

            //Просто данные.
            List<Statistics> statisticsList = dataManager.load(Statistics.class).all().sort(Sort.by("finishDate")).list();
            if (statisticsList.size() != 0) {
                for (Statistics stat : statisticsList) {
                    if (Objects.equals(stat.getStatistics_to_user().getUsername(), currentAuthentication.getUser().getUsername())) {
                        UserProgress up = new UserProgress(stat.getStatistics_to_exercise().getName() + "\n"
                                + formatter.format(stat.getFinishDate()), stat.getTimer());
                        userProgressList.add(up);
                    }
                }
            }
            /*statisticsList = statisticsList.stream().filter(
                    stat -> Objects.equals(stat.getStatistics_to_user().getUsername(),
                            currentAuthentication.getUser().getUsername())
            ).collect(Collectors.toList());*/
            adminSerialChart.setCategoryField("exercise");
            statisticsesUserDc.setItems(userProgressList);
            return;
        }

        /*UUID result = null;
        String userName = currentAuthentication.getUser().getUsername().toString();
        List<User> users = dataManager.load(User.class).all().list();
        if (users.size() != 0) {
            for (User user : users) {
                if (Objects.equals(user.getUsername(), userName))
                    result = user.getId();
            }
        }

        User currentUser = dataManager.load(User.class).id(result).one();*/

        Collection<UserProgress> userProgressList = new ArrayList<>();
        Map<UUID, Integer> userStates = new HashMap<>();

        //Считаем среднее за упражнения у пользователя.
        List<User> userList = dataManager.load(User.class).all().list();
        List<Statistics> statisticsList = dataManager.load(Statistics.class).all().sort(Sort.by("finishDate")).list();
        if (statisticsList.size() != 0 && userList.size() != 0) {
            int counter = 0;
            for (Statistics stat : statisticsList) {
                if (Objects.equals(stat.getStatistics_to_user().getUsername(),  currentAuthentication.getUser().getUsername())
                        && Objects.equals(stat.getStatistics_to_exercise().getName(),  exerciseComboBox.getValue().getName())) {
                    UserProgress up = new UserProgress(stat.getStatistics_to_exercise().getName() + "\n"
                            + formatter.format(stat.getFinishDate()), stat.getTimer());
                    userProgressList.add(up);
                }
            }
        }
        //Меняем параметры графика
        adminSerialChart.setCategoryField("exercise");
        statisticsesUserDc.setItems(userProgressList);

    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        List<Statistics> statistics = dataManager.load(Statistics.class).query(
                "select e from Statistics_ e order by e.finishDate").sort(Sort.by("finishDate")).list();
        statistics = statistics.stream().filter(
                stat -> Objects.equals(stat.getStatistics_to_user().getUsername(),
                        currentAuthentication.getUser().getUsername())
        ).collect(Collectors.toList());

        statisticsesDc.setItems(statistics);

    }

    @Subscribe
    public void onInit(InitEvent event) {
        List<Statistics> statistics = dataManager.load(Statistics.class).query(
                "select e from Statistics_ e order by e.finishDate").sort(Sort.by("finishDate")).list();
        statistics = statistics.stream().filter(
                stat -> Objects.equals(stat.getStatistics_to_user().getUsername(),
                        currentAuthentication.getUser().getUsername())
        ).collect(Collectors.toList());

        statisticsesDc.setItems(statistics);
    }

}