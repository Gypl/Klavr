package com.company.klavr.screen.exercise;

import io.jmix.ui.Screens;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.*;
import com.company.klavr.entity.Exercise;
import io.jmix.ui.sys.ScreensImpl;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("ExerciseUser.browse")
@UiDescriptor("exerciseUser-browse.xml")
@LookupComponent("exercisesTable")
public class ExerciseUserBrowse extends StandardLookup<Exercise> {

    @Autowired
    private Screens screens;
    @Autowired
    private GroupTable<Exercise> exercisesTable;

    @Subscribe("exercisesTable")
    public void onExercisesTableSelection(Table.SelectionEvent<Exercise> event) {
        event.getSelected().forEach(
            exercise -> {
                if (exercise != null) {
                    ExerciseScreen exerciseScreen = screens.create(ExerciseScreen.class);
                    exerciseScreen.setExercise(exercise);
                    screens.show(exerciseScreen);
                }
            }
        );
    }
}