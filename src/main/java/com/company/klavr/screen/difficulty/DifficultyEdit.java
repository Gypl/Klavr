package com.company.klavr.screen.difficulty;

import io.jmix.ui.screen.*;
import com.company.klavr.entity.Difficulty;

@UiController("Difficulty.edit")
@UiDescriptor("difficulty-edit.xml")
@EditedEntityContainer("difficultyDc")
public class DifficultyEdit extends StandardEditor<Difficulty> {
}