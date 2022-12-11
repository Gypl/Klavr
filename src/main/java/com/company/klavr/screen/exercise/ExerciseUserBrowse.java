package com.company.klavr.screen.exercise;

import io.jmix.ui.screen.*;
import com.company.klavr.entity.Exercise;

@UiController("ExerciseUser.browse")
@UiDescriptor("exerciseUser-browse.xml")
@LookupComponent("exercisesTable")
public class ExerciseUserBrowse extends StandardLookup<Exercise> {
}