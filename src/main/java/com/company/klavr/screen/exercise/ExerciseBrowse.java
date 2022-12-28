package com.company.klavr.screen.exercise;

import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.Table;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.klavr.entity.Exercise;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Exercise.browse")
@UiDescriptor("exercise-browse.xml")
@LookupComponent("exercisesTable")
public class ExerciseBrowse extends StandardLookup<Exercise> {
    @Autowired
    private DataManager dataManager;
    @Autowired
    private GroupTable<Exercise> exercisesTable;
    @Autowired
    private CollectionLoader<Exercise> exercisesDl;
    @Autowired
    private Notifications notifications;
    @Autowired
    private Messages messages;
    @Autowired
    private Button removeBtn;

/*    @Subscribe("removeBtn")
    public void onRemoveBtnClick(Button.ClickEvent event) {
        Exercise exer = exercisesTable.getSingleSelected();
        if (dataManager.load(Exercise.class).query("select e from Statistics_ e where e.statistics_to_exercise = ?1",
                exer.getName()).list().size() != 0){
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(messages.getMessage("com.company.klavr.exerciseInUse")).show();
        } else {
            dataManager.remove(exer);
            exercisesDl.load();
        }
        removeBtn.setEnabled(false);
    }*/

/*    @Subscribe("exercisesTable")
    public void onExercisesTableSelection(Table.SelectionEvent<Exercise> event) {
        removeBtn.setEnabled(exercisesTable.getSingleSelected() != null);
    }*/

}