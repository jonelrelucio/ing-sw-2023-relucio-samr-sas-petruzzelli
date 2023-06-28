package it.polimi.ingsw.client.view.gui.guiController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerMyShelfie {

    private static ViewGui viewGUI;
    @FXML
    Button howToPlay;
    @FXML
    Button letsPlay;



    public void howToPlay(ActionEvent actionEvent) {

    }

    public void letsPlay(ActionEvent actionEvent) {
        viewGUI = new ViewGui((Stage)letsPlay.getScene().getWindow());
        viewGUI.showMain();
    }
}
