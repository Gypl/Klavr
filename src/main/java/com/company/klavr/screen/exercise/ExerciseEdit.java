package com.company.klavr.screen.exercise;

import com.company.klavr.entity.Difficulty;
import com.company.klavr.entity.Zone;
import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.*;
import io.jmix.ui.screen.*;
import com.company.klavr.entity.Exercise;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Scanner;
import java.util.Random;

@UiController("Exercise.edit")
@UiDescriptor("exercise-edit.xml")
@EditedEntityContainer("exerciseDc")
public class ExerciseEdit extends StandardEditor<Exercise> {
    @Autowired
    private DataManager dataManager;
    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if(exercise_to_difficultyComboBox.getValue() == null) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(messages.getMessage("com.company.klavr.NoDifficulty")).show();
        }
        else {
            Difficulty curDiff = exercise_to_difficultyComboBox.getValue();

            List<Zone> zones = curDiff.getDifficult_to_zone();
            String symbols = "[^\s";
            for(Zone zone : zones){
                symbols+=zone.getSymbols();
            }
            symbols+=" ]";
            textField.setValue(textField.getValue().replaceAll(symbols, ""));

            int textLgth = textField.getValue().length();
            if((textLgth < curDiff.getMinLength()) || (textLgth > curDiff.getMaxLength()) || (textLgth < curDiff.getMinLength() && textLgth > curDiff.getMaxLength())){
                notifications.create(Notifications.NotificationType.TRAY)
                        .withCaption(messages.getMessage("com.company.klavr.incorrectLength")).show();
                event.preventCommit();
            }
        }
    }
    @Autowired
    private TextArea<String> textField;
    @Autowired
    private FileUploadField loadFile;
    @Autowired
    private TextField<Integer> lengthField;
    @Autowired
    private Notifications notifications;
    @Autowired
    private Messages messages;
    @Autowired
    private EntityComboBox<Difficulty> exercise_to_difficultyComboBox;

    @Subscribe("loadFile")
    public void onLoadFileFileUploadSucceed(SingleFileUploadField.FileUploadSucceedEvent event) {
        if(!event.getFileName().contains(".xrc")){
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(messages.getMessage("com.company.klavr.XRCnotifiaction")).show();
        }
        else {
            Scanner fileText = new Scanner(loadFile.getFileContent(), "UTF-8").useDelimiter("\\A");
            String result = fileText.hasNext() ? fileText.next() : "";
            textField.setValue(result);
        }
    }

    @Subscribe("generateText")
    public void onGenerateTextClick(Button.ClickEvent event) {
        if(exercise_to_difficultyComboBox.getValue() == null) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(messages.getMessage("com.company.klavr.NoDifficulty")).show();
        }
        else{
            Difficulty curDiff = exercise_to_difficultyComboBox.getValue();
            int curLength = curDiff.getMaxLength();

            List<Zone> zones = curDiff.getDifficult_to_zone();
            String symbols = "";
            for(Zone zone : zones){
                symbols+=zone.getSymbols();
            }

            String generatedText = "";
            for(int i = 0;i<curLength;i++){
                if(Math.random()<0.25 && generatedText.length() != 0 && generatedText.length() != curLength-1 && !generatedText.endsWith(" "))
                    generatedText+=" ";
                else
                    generatedText+= symbols.toCharArray()[new Random().nextInt(symbols.length())];
            }

            textField.setValue(generatedText);
        }
    }

    @Subscribe("textField")
    public void onTextFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        lengthField.setValue(event.getValue().length());
    }

}