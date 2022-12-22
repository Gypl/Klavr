package com.company.klavr.screen.info;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("InfoScreen")
@UiDescriptor("Info-screen.xml")
public class InfoScreen extends Screen {
    @Autowired
    private Notifications notifications;
    @Subscribe("creatorsButton")
    public void onCreatorsButtonClick(Button.ClickEvent event) {
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("Андрей, Мишаня, Даня, Фапах").show();
    }
}