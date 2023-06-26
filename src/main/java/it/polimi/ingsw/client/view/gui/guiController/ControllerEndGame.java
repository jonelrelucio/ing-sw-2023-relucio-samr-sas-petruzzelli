package it.polimi.ingsw.client.view.gui.guiController;

import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;



public class ControllerEndGame{
    @FXML
    GridPane gameResultGridPane;
    @FXML
    Label nameFirstPlaceLabel;
    @FXML
    Label nameSecondPlaceLabel;
    @FXML
    Label nameThirdPlaceLabel;
    @FXML
    Label nameFourthPlaceLabel;
    @FXML
    Label scoreFirstPlaceLabel;
    @FXML
    Label scoreSecondPlaceLabel;
    @FXML
    Label scoreThirdPlaceLabel;
    @FXML
    Label scoreFourthPlaceLabel;

    public void init(GameModelView gameModelView){

    }
}
