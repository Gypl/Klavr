package com.company.klavr.screen.difficulty;

import com.company.klavr.entity.Zone;
import io.jmix.core.DataManager;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.CheckBoxGroup;
import io.jmix.ui.component.Slider;
import io.jmix.ui.component.TextField;
import io.jmix.ui.component.TextInputField;
import io.jmix.ui.component.data.options.ContainerOptions;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import com.company.klavr.entity.Difficulty;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UiController("Difficulty.edit")
@UiDescriptor("difficulty-edit.xml")
@EditedEntityContainer("difficultyDc")

public class DifficultyEdit extends StandardEditor<Difficulty> {
    @Autowired
    private CheckBoxGroup<Zone> zoneTableCheckBoxGroup;
    @Autowired
    private CollectionContainer<Zone> zonesDc;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private TextField<String> nameField;
    @Autowired
    private Notifications notifications;
    @Autowired
    private Slider<Integer> pressTimeSlider;
    @Autowired
    private Slider<Integer> mistakesSlider;
    @Autowired
    private Slider<Integer> minLengthSlider;
    @Autowired
    private Slider<Integer> maxLengthSlider;

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        List<Difficulty> difficulties = dataManager.load(Difficulty.class).all().list();
        List<String> names = new ArrayList<>();
        List<UUID> ids = new ArrayList<>();
        for (Difficulty diff : difficulties) {
            names.add(diff.getName());
            ids.add(diff.getId());
        }
        if (!ids.contains(getEditedEntity().getId()) && names.contains(nameField.getValue())){
            notifications.create(Notifications.NotificationType.HUMANIZED)
                    .withCaption("Сложность с данным именем уже существует").show();
            event.preventCommit();
        }
    }

    @Subscribe("nameField")
    public void onNameFieldTextChange(TextInputField.TextChangeEvent event) {
        if (pressTimeSlider.getValue() == null)
            getEditedEntity().setPressTime(pressTimeSlider.getMin());
        if (mistakesSlider.getValue() == null)
            getEditedEntity().setMistakesCount(mistakesSlider.getMin());
        if (minLengthSlider.getValue() == null)
            getEditedEntity().setMinLength(minLengthSlider.getMin());
        if (maxLengthSlider.getValue() == null)
            getEditedEntity().setMaxLength(maxLengthSlider.getMin());
    }


    @Subscribe
    public void onInit(InitEvent event) {
        zoneTableCheckBoxGroup.setOptions(new ContainerOptions<>(zonesDc));
    }
}