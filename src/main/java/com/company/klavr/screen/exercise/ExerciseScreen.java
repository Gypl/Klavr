package com.company.klavr.screen.exercise;

import io.jmix.ui.component.*;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("ExerciseScreen")
@UiDescriptor("Exercise-screen.xml")
public class ExerciseScreen extends Screen {

    @Autowired
    private Label writtenText;
    @Autowired
    private Label text;
    @Autowired
    private TextField anton;

    private void updateUserInput(String input) {
        System.out.println("----------------------");
        System.out.println(input);
        System.out.println("----------------------");
        String str = text.getRawValue();
        text.setValue(str.substring(1, str.length()-1));

        writtenText.setValue(writtenText.getRawValue()+input);
    }

    @Subscribe("anton")
    public void onAntonValueChange(HasValue.ValueChangeEvent event) {
        if(anton.getRawValue() != "") {
            updateUserInput(anton.getRawValue());
            anton.setValue("");
        }
    }

    @Subscribe
    public void onInit(InitEvent event) {
        text.setValue("text me");
    }

    @Subscribe("mainScreen")
    public void onMainScreenLayoutClick(LayoutClickNotifier.LayoutClickEvent event) {
        //focus input
        anton.focus();
    }
}