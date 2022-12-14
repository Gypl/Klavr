package com.company.klavr.screen.exercise;

import io.jmix.ui.component.TextField;
import io.jmix.ui.component.TextInputField;
import io.jmix.ui.screen.*;
import com.company.klavr.entity.Exercise;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Exercise.edit")
@UiDescriptor("exercise-edit.xml")
@EditedEntityContainer("exerciseDc")
public class ExerciseEdit extends StandardEditor<Exercise> {
    @Subscribe("textField")
    public void onTextFieldTextChange(TextInputField.TextChangeEvent event) {
        lengthField.setValue(event.getText().length());
    }
    @Autowired
    private TextField<Integer> lengthField;
}