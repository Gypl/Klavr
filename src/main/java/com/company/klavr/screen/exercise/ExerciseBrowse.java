package com.company.klavr.screen.exercise;

import io.jmix.ui.component.Table;
import io.jmix.ui.screen.*;
import com.company.klavr.entity.Exercise;

@UiController("Exercise.browse")
@UiDescriptor("exercise-browse.xml")
@LookupComponent("exercisesTable")
public class ExerciseBrowse extends StandardLookup<Exercise> {

}