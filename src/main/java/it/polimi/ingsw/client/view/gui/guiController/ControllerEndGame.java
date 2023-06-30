package it.polimi.ingsw.client.view.gui.guiController;

import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
    private static final int height = 45;
    private static final int width = 40;
    /**
     * Fills the grid pane with the name of the players and their points from the highest score to the lowest
     * @param gameModelView
     */
    public void init(GameModelView gameModelView){
        int[] pointsList = gameModelView.getPointsList();
        int[] orderedPoints = Arrays.copyOf(pointsList, pointsList.length);
        String[] playerList = new String[pointsList.length];
        Arrays.sort(orderedPoints);

        int size = playerList.length ;
        int i = 0;
        for (int point : pointsList) {
            int position = Arrays.binarySearch(orderedPoints, point);
            playerList[position] = gameModelView.getPlayerList()[i];
            if(position==size-1) firstPlayerPosition = i;
            i++;
        }

        scoreFirstPlaceLabel.setText(String.valueOf(orderedPoints[size-1]));
        nameFirstPlaceLabel.setText(playerList[size-1]);
        theWinnerIsLabel.setText("The winner is :\n" + playerList[size-1]);
        scoreSecondPlaceLabel.setText(String.valueOf(orderedPoints[size-2]));
        nameSecondPlaceLabel.setText(playerList[size-2]);
        if(playerList.length == 2){
            nameThirdPlaceLabel.setText("");
            scoreThirdPlaceLabel.setText("");
            nameFourthPlaceLabel.setText("");
            scoreFourthPlaceLabel.setText("");
        } else if (playerList.length == 3) {
            nameFourthPlaceLabel.setText("");
            scoreFourthPlaceLabel.setText("");
            scoreThirdPlaceLabel.setText(String.valueOf(orderedPoints[size-3]));
            nameThirdPlaceLabel.setText(playerList[size-3]);
        }else{
            scoreFourthPlaceLabel.setText(String.valueOf(orderedPoints[size-4]));
            nameFourthPlaceLabel.setText(playerList[size-4]);
        }
    }
    
    /**
     * Set the field 'viewGUI'
     * @param viewGui
     */
    public void setViewGui(ViewGui viewGui) {
        viewGUI = viewGui;
    }
    /**
     * Fills the grid pane bookshelf with the winner book shelf
     * @param gameModelView
     * @see #createImageTile(ItemTileType, int) 
     */
    public void setLeaderBookShelf(GameModelView gameModelView) {
        ItemTileType[][] t = gameModelView.getBookshelfList()[firstPlayerPosition];
        int[][] id = gameModelView.getBookshelfListItemId()[firstPlayerPosition];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if(t[i][j] != ItemTileType.EMPTY) {
                    ImageView im = createImageTile(t[i][j],id[i][j]);
                    winnerBookShelfGrid.add(im,j,i);
                    GridPane.setMargin(im,new Insets(0,0,0,5));
                }
            }
        }
    }
    /**
     *Returns an image view with the correspondent Item tile image set
     * @param id
     * @param t
     * @return
     */
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