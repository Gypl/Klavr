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

    private String written = "";
    private String needed = "";

    public ExerciseScreen() {
        needed = "тест очка";
    }

    @Subscribe
    public void onInit(InitEvent event) {
        needed = needed.replaceAll(" ", "\u00A0");
        text.setValue(needed);
    }

    @Subscribe("anton")
    public void onAntonValueChange(HasValue.ValueChangeEvent event) {
        if(anton.getRawValue() == "") {
            return;
        }
        if (needed == "") {
            return;
        }
        handleInput();
    }

    private void exerciseDone() {
        System.out.println("Well done");
    }

    private void wrongSymbol() {
        System.err.println("Err");
    }

    private void handleInput() {
        char inputSymbol = anton.getRawValue().charAt(0) == ' ' ? '\u00A0' : anton.getRawValue().charAt(0);
        anton.setValue("");
        if (correctSymbol(inputSymbol)) {
            updateUserInput();
            if (needed == "") {
                exerciseDone();
                return;
            }
        }
        else {
            wrongSymbol();
        }
    }

    private void updateUserInput() {
        if (needed != "") {
            char changed = needed.charAt(0);
            needed = getText(needed);
            text.setValue(needed);
            written += changed == ' ' ? "\u00A0":changed;
            writtenText.setValue(written);
        }
    }

    private String getText(String old) {
        return old.substring(1, old.length());
    }

    private boolean correctSymbol(char inputSymbol) {
        return inputSymbol == needed.charAt(0);
    }

    @Subscribe("mainScreen")
    public void onMainScreenLayoutClick(LayoutClickNotifier.LayoutClickEvent event) {
        anton.focus();
    }
}