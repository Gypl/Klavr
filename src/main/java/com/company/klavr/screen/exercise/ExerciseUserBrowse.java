package com.company.klavr.screen.exercise;

import com.company.klavr.entity.Difficulty;
import io.jmix.core.DataManager;
import io.jmix.core.Sort;
import io.jmix.ui.Screens;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.Table;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import com.company.klavr.entity.Exercise;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@UiController("ExerciseUser.browse")
@UiDescriptor("exerciseUser-browse.xml")
@LookupComponent("exercisesTable")
public class ExerciseUserBrowse extends StandardLookup<Exercise> {

    @Autowired
    private Screens screens;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private CollectionContainer<Exercise> exercisesDc;
    @Autowired
    private EntityComboBox<Difficulty> difficultyComboBox;

    @Subscribe("difficultyComboBox")
    public void onDifficultyComboBoxValueChange(HasValue.ValueChangeEvent<Difficulty> event) {
        Collection<Exercise> exercises = dataManager.load(Exercise.class).query(
                "select e from Exercise e order by e.exercise_to_difficulty.name ").sort(Sort.by("name")).list();
        if (difficultyComboBox.getValue() != null) {
            exercises = exercises.stream().filter(
                    stat -> Objects.equals(stat.getExercise_to_difficulty().getId(),
                            difficultyComboBox.getValue().getId())
            ).collect(Collectors.toList());
            exercisesDc.setItems(exercises);
        }
        exercisesDc.setItems(exercises);
    }

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