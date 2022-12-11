package com.company.klavr.screen.difficulty;

import io.jmix.ui.screen.*;
import com.company.klavr.entity.Difficulty;

@UiController("Difficulty.browse")
@UiDescriptor("difficulty-browse.xml")
@LookupComponent("difficultiesTable")
public class DifficultyBrowse extends StandardLookup<Difficulty> {
}