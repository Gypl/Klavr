package com.company.klavr.screen.difficulty;

import com.company.klavr.entity.Zone;
import io.jmix.ui.component.CheckBoxGroup;
import io.jmix.ui.component.data.options.ContainerOptions;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import com.company.klavr.entity.Difficulty;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Difficulty.edit")
@UiDescriptor("difficulty-edit.xml")
@EditedEntityContainer("difficultyDc")

public class DifficultyEdit extends StandardEditor<Difficulty> {
    @Autowired
    private CheckBoxGroup<Zone> zoneTableCheckBoxGroup;
    @Autowired
    private CollectionContainer<Zone> zonesDc;

    @Subscribe
    public void onInit(InitEvent event) {
        zoneTableCheckBoxGroup.setOptions(new ContainerOptions<>(zonesDc));
    }
}