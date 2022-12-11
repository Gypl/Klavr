package com.company.klavr.screen.exercise;

import io.jmix.ui.screen.*;
import com.company.klavr.entity.Exercise;

@UiController("Exercise.edit")
@UiDescriptor("exercise-edit.xml")
@EditedEntityContainer("exerciseDc")
public class ExerciseEdit extends StandardEditor<Exercise> {
}