package com.company.klavr.screen.info;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.MessageDialogFacet;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("InfoScreen")
@UiDescriptor("Info-screen.xml")
public class InfoScreen extends Screen {
    @Autowired
    private MessageDialogFacet messageDialog;

    @Subscribe("creatorsButton")
    public void onCreatorsButtonClick(Button.ClickEvent event) {
        messageDialog.setCaption("О разработчиках");
        messageDialog.setMessage(getCreators());
        messageDialog.show();
    }

    private String getCreators() {
        return String.format("Данную систему разработали студенты группы 6402-090301D: \n") +
                String.format("Лавлов А.Ю.\n") +
                String.format("Денисов М.Н.\n") +
                String.format("Косарев Д.С.\n");
    }
}