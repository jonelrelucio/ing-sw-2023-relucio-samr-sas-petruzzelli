package it.polimi.ingsw.client.view.gui.guiController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerWaitingForPlayers implements Initializable {
    @FXML
    Label messageLabel;


    public void showMessage(String s){
        messageLabel.setText(s);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageLabel.setText("");
    }
}
