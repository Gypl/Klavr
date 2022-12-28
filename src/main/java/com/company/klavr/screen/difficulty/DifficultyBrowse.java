package com.company.klavr.screen.difficulty;

import com.company.klavr.entity.Exercise;
import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.NotificationFacet;
import io.jmix.ui.component.Table;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.klavr.entity.Difficulty;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Difficulty.browse")
@UiDescriptor("difficulty-browse.xml")
@LookupComponent("difficultiesTable")
public class DifficultyBrowse extends StandardLookup<Difficulty> {
    @Autowired
    private GroupTable<Difficulty> difficultiesTable;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Notifications notifications;

    @Autowired
    private Messages messages;
    @Autowired
    private CollectionLoader<Difficulty> difficultiesDl;
    @Autowired
    private Button removeBtn;

    @Subscribe("removeBtn")
    public void onRemoveBtnClick(Button.ClickEvent event) {
        Difficulty dif = difficultiesTable.getSingleSelected();
        dataManager.load(Exercise.class).query("select e from Exercise e where e.exercise_to_difficulty.id = ?1",
                dif.getId()).list();
        if (dataManager.load(Exercise.class).query("select e from Exercise e where e.exercise_to_difficulty.id = ?1",
                dif.getId()).list().size() != 0){
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(messages.getMessage("com.company.klavr.difficultiInUse")).show();
        } else {
            dataManager.remove(dif);
            difficultiesDl.load();
            removeBtn.setEnabled(false);
        }
    }

    @Subscribe("difficultiesTable")
    public void onDifficultiesTableSelection(Table.SelectionEvent<Difficulty> event) {
        removeBtn.setEnabled(difficultiesTable.getSingleSelected() != null);
    }

}