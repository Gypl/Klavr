package com.company.klavr.screen.info;

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
        helpMe();
    }

    private String getCreators() {
        return String.format("Лабораторный практикум по дисциплине: \n \"Технологии программирования\" \n Тема: " +
                " \"Клавиатурный тренажёр с функциями администрирования\"\n\n") +
                String.format("Данную систему разработали обучающиеся группы 6402-090301D: \n") +
                String.format("Лавлов А.Ю.\n") +
                String.format("Денисов М.Н.\n") +
                String.format("Косарев Д.С.\n\n") +
                String.format("Руководитель проекта: \n") +
                String.format("Зеленко Лариса Сергеевна\n") +
                String.format("\nСамарский университет 2022\n");
    }
    private void helpMe(){
//        WebView webView = new WebView();
//        WebEngine webEngine = webView.getEngine();
//        URL url = getClass().getResource("help.html");
//        webEngine.load(url.toExternalForm());

    }
}