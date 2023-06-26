package it.polimi.ingsw.client.view.gui.guiController;

import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Arrays;


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
    @FXML
    Label theWinnerIsLabel;

    public void init(GameModelView gameModelView){
        int[] orderedPoints = gameModelView.getPointsList();
        Arrays.sort(orderedPoints);
        String [] playerList  = orderPlayerList(gameModelView.getPlayerList(),gameModelView.getPointsList(),orderedPoints);
        scoreFirstPlaceLabel.setText(String.valueOf(orderedPoints[0]));
        nameFirstPlaceLabel.setText(playerList[0]);
        theWinnerIsLabel.setText("The winner is :\n" + playerList[0]);
        scoreSecondPlaceLabel.setText(String.valueOf(orderedPoints[1]));
        nameSecondPlaceLabel.setText(playerList[1]);
        if(playerList.length == 2){
            gameResultGridPane.getRowConstraints().remove(3);
            gameResultGridPane.getRowConstraints().remove(2);
        } else if (playerList.length == 3) {
            gameResultGridPane.getRowConstraints().remove(3);
            scoreThirdPlaceLabel.setText(String.valueOf(orderedPoints[2]));
            nameThirdPlaceLabel.setText(playerList[2]);
        }else{
            scoreFourthPlaceLabel.setText(String.valueOf(orderedPoints[3]));
            nameFourthPlaceLabel.setText(playerList[3]);
        }
    }

    //Non ci sono controlli su elementi con lo stesso punteggio
    private String[] orderPlayerList(String[] players ,int[] points,int[] orderedPoints) {
        String [] orderedPlayers = new String[players.length];
        for (int j=0; j<points.length;j++) {
            for (int i =0;i< orderedPoints.length ; i++){
                if(points[j] == orderedPoints[i]){
                    orderedPlayers[i] = players[j];
                }
            }
        }
        return orderedPlayers;
    }
}
