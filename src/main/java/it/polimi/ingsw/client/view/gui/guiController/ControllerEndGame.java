package it.polimi.ingsw.client.view.gui.guiController;

import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML
    GridPane winnerBookShelfGrid;
    private static ViewGui viewGUI;
    private int firstPlayerPosition = 0;
    private static final int height = 40;
    private static final int width = 35;

    public void init(GameModelView gameModelView){
        int[] orderedPoints = gameModelView.getPointsList();
        Arrays.sort(orderedPoints);
        int size = orderedPoints.length;
        String [] playerList  = orderPlayerList(gameModelView.getPlayerList(),gameModelView.getPointsList(),orderedPoints);
        scoreFirstPlaceLabel.setText(String.valueOf(orderedPoints[size-1]));
        nameFirstPlaceLabel.setText(playerList[size-1]);
        theWinnerIsLabel.setText("The winner is :\n" + playerList[size-1]);
        scoreSecondPlaceLabel.setText(String.valueOf(orderedPoints[size-2]));
        nameSecondPlaceLabel.setText(playerList[size-2]);
        if(playerList.length == 2){
            gameResultGridPane.getChildren().remove(3);
            gameResultGridPane.getChildren().remove(2);
        } else if (playerList.length == 3) {
            gameResultGridPane.getChildren().remove(3);
            scoreThirdPlaceLabel.setText(String.valueOf(orderedPoints[size-3]));
            nameThirdPlaceLabel.setText(playerList[size-3]);
        }else{
            scoreFourthPlaceLabel.setText(String.valueOf(orderedPoints[size-4]));
            nameFourthPlaceLabel.setText(playerList[size-4]);
        }
    }

    //Non ci sono controlli su elementi con lo stesso punteggio
    private String[] orderPlayerList(String[] players ,int[] points,int[] orderedPoints) {
        String [] orderedPlayers = new String[players.length];
        for (int j=0; j<points.length;j++) {
            for (int i =0;i< orderedPoints.length ; i++){
                if(points[j] == orderedPoints[i]){
                    orderedPlayers[i] = players[j];
                    if(i == orderedPoints.length - 1){
                        firstPlayerPosition = j;
                    }
                }
            }
        }
        return orderedPlayers;
    }

    public void setViewGui(ViewGui viewGui) {
        viewGUI = viewGui;
    }

    public void setLeaderBookShelf(GameModelView gameModelView) {
        ItemTileType[][] t = gameModelView.getBookshelfList()[firstPlayerPosition];
        int[][] id = gameModelView.getBookshelfListItemId()[firstPlayerPosition];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if(t[i][j] != ItemTileType.EMPTY) {
                    winnerBookShelfGrid.add(createImageTile(t[i][j],id[i][j]),j,i);
                }
            }
        }
    }

    private ImageView createImageTile(ItemTileType t, int id){
        ImageView imageView = new ImageView();
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);
        Image image = new Image(getClass().getResource("/view/gui/item_tiles/" + t + "1."+ id +".png").toString());
        imageView.setImage(image);
        return imageView;
    }
}
