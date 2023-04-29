package it.polimi.ingsw.client.controller.guicontroller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMainScene implements Initializable {


    @FXML
    BorderPane parentPane;
    private static ViewGui viewGUI;

    public void setViewGui(ViewGui viewGUI) {
        ControllerMainScene.viewGUI = viewGUI;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
