//package it.polimi.ingsw.client.view.gui.guiController;
//
//
//import it.polimi.ingsw.client.view.View;
//import it.polimi.ingsw.client.view.cli.Const;
//import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
//import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
//import it.polimi.ingsw.distributed.events.controllerEvents.MessageEvent;
//import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.control.Label;
//import javafx.scene.control.Tab;
//import javafx.scene.control.TabPane;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.GridPane;
//import javafx.scene.text.Text;
//import javafx.scene.text.TextFlow;
//
//import java.net.URL;
//import java.util.*;
//
//import static it.polimi.ingsw.distributed.events.controllerEvents.EventController.*;
//
//public class ControllerMainScene implements Initializable {
//
//
//    @FXML
//    BorderPane parentPane;
//    @FXML
//    GridPane boardGrid;
//
//    @FXML
//    Label provaMex;
//
//     @FXML
//     TextFlow textMessage;
//    Text text;
//
//    @FXML
//    Tab personalTab;
//    @FXML
//    GridPane personalGrid;
//    @FXML
//    Tab secondPlayerTab;
//    @FXML
//    Tab thirdPlayerTab;
//    @FXML
//    Tab fourthPlayerTab;
//    @FXML
//    GridPane secondPlayerPane;
//    @FXML
//    GridPane thirdPlayerPane;
//    @FXML
//    GridPane fourthPlayerPane;
//    @FXML
//    TabPane otherPlayers;
//
//
//    private static ViewGui viewGUI;
//
//
//    private ImageView[][] cells;
//
//    public void setViewGui(ViewGui viewGUI) {
//        this.viewGUI = viewGUI;
//    }
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        text = new Text("Il gioco è iniziato");
//        textMessage = new TextFlow(text);
//        provaMex.setText("Il gioco è iniziato");
//        //text = new Text("It's "+ gameModelView.getCurrentPlayer() + "'s turn.\n");
//        //textMessage = new TextFlow(text);
//    }
//
//
//    public void showMessage(String s){
//        text = new Text(s);
//        textMessage = new TextFlow(text);
//    }
//
//    private boolean containsCoordinates(ArrayList<int[]> list, int i, int j){
//        int[] coordinates = new int[]{i, j};
//        for (int[] array : list) {
//            if (Arrays.equals(array, coordinates)) return true;
//        }
//        return false;
//    }
//
//
//    public void showBoard(GameModelView gameModelView){
//        text = new Text("trying to print");
//        textMessage = new TextFlow(text);
//        provaMex.setText("cerca di fare board");
//        ItemTileType[][] board = gameModelView.getBoardMatrix();
//        for (int i = 1; i < board.length-1; i++){
//            for (int j = 1; j < board.length-1; j++) {
//                if(board[i][j] == ItemTileType.EMPTY) {
//                    setNodeBoardTile(boardGrid,i-1,j-1,board[i][j]);
//                }
//                else if (containsCoordinates( gameModelView.getCanBeSelectedCoordinates(), i, j))
//                    setNodeBoardTile(boardGrid,i-1,j-1,board[i][j]);
//                else if (containsCoordinates(gameModelView.getSelectedCoordinates(), i, j))
//                    setNodeBoardSelected(boardGrid,i-1,j-1,board[i][j]);
//                else
//                    setNodeBoardTile(boardGrid,i-1,j-1,board[i][j]);
//            }
//        }
//    }
//
//
//    private void setNodeBoardTile (GridPane gridPane,int row, int col,ItemTileType t){
//        for (Node node : gridPane.getChildren()) {
//            Integer c = GridPane.getColumnIndex(node);
//            Integer r = GridPane.getRowIndex(node);
//            if (c == col && r == row && (node instanceof ImageView)) {
//                ImageView imageView = (ImageView) node;
//                if(t == ItemTileType.EMPTY) {
//                    imageView.setDisable(true);
//                }else  {
//                    Random random = new Random();
//                    Integer ran = random.nextInt(4 - 1) + 1;
//                    Image image = new Image(getClass().getResource("/view/gui/item_tiles/" + t + "1."+ ran +".png").toString());
//                    imageView.setImage(image);
//                }
//            }
//        }
//    }
//
//    private void setNodeBoardSelected (GridPane gridPane,int row, int col,ItemTileType t){
//        for (Node node : gridPane.getChildren()) {
//            Integer c = GridPane.getColumnIndex(node);
//            Integer r = GridPane.getRowIndex(node);
//            if (c == col && r == row && (node instanceof ImageView)) {
//                ImageView imageView = (ImageView) node;
//                Random random = new Random();
//                Integer ran = random.nextInt(4 - 1) + 1;
//                Image image = new Image(getClass().getResource("/view/gui/item_tiles/" + t + "1."+ ran +".png").toString());
//                imageView.setImage(image);
//                imageView.setDisable(false);
//                imageView.setOpacity(50);
//            }
//        }
//    }
//
//    public void showTabPlayers(String [] players){
//        if(players.length==2){
//            otherPlayers.getTabs().remove(thirdPlayerTab);
//            otherPlayers.getTabs().remove(fourthPlayerTab);
//        } else if (players.length==3) {
//            otherPlayers.getTabs().remove(fourthPlayerTab);
//        }
//    }
//    public void showBookshelves(GameModelView gameModelView) {
//        String [] players = gameModelView.getPlayerList();
//        showTabPlayers(players);
//        provaMex.setText(viewGUI.getThisUsername());
//        for (int i = 0; i < players.length; i++) {
//            if(players[i].equals(viewGUI.getThisUsername())){
//                setDetailesPlayer(personalTab,personalGrid,players[i],gameModelView.getBookshelfList()[i]);
//            } else {
//                setDetailesPlayer(secondPlayerTab,secondPlayerPane,players[i],gameModelView.getBookshelfList()[i]);
//            }
//        }
//    }
//
//    public void setDetailesPlayer(Tab t,GridPane g, String name, ItemTileType [][] bookShelf){
//        t.setText(name);
//        for (int i = 0; i < 6; i++) {
//            for (int j = 0; j < 5; j++) {
//                for (Node node : g.getChildren()) {
//                    Integer c = GridPane.getColumnIndex(node);
//                    Integer r = GridPane.getRowIndex(node);
//                    if (c == j && r == i && (node instanceof ImageView)) {
//                        ImageView imageView = (ImageView) node;
//                        if(bookShelf[i][j] == ItemTileType.EMPTY) {
//                            imageView.setDisable(true);
//                        }else  {
//                            Random random = new Random();
//                            Integer ran = random.nextInt(4 - 1) + 1;
//                            Image image = new Image(getClass().getResource("/view/gui/item_tiles/" + t + "1."+ ran +".png").toString());
//                            imageView.setImage(image);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//}
//
