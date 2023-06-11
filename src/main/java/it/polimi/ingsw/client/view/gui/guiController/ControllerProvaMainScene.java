//package it.polimi.ingsw.client.view.gui.guiController;
//
//
//import it.polimi.ingsw.client.view.cli.Const;
//import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
//import it.polimi.ingsw.distributed.events.controllerEvents.MessageEvent;
//import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.Tab;
//import javafx.scene.control.TabPane;
//import javafx.scene.effect.InnerShadow;
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
//import static it.polimi.ingsw.distributed.events.controllerEvents.EventController.DESELECT_COORDINATES;
//
//public class ControllerProvaMainScene implements Initializable {
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
//   private  ArrayList<Button> buttons;
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
//        showMessage("Il gioco è iniziato");
//        provaMex.setText("Il gioco è iniziato");
//        //text = new Text("It's "+ gameModelView.getCurrentPlayer() + "'s turn.\n");
//        //textMessage = new TextFlow(text);
//    }
//
//
//    public void showMessage(String s){
//        text = new Text(s + "\n");
//        textMessage.getChildren().add(text);
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
//        showMessage("trying to print");
//        provaMex.setText("cerca di fare board");
//        ItemTileType[][] board = gameModelView.getBoardMatrix();
//        buttons = new ArrayList<>();
//        for (int i = 1; i < board.length-1; i++){
//            for (int j = 1; j < board.length-1; j++) {
//                if(board[i][j] == ItemTileType.EMPTY) {
//                    setNodeBoardTile(boardGrid,i-1,j-1,board[i][j]);
//                }
//                else if (containsCoordinates( gameModelView.getCanBeSelectedCoordinates(), i, j))
//                    setNodeBoardTileButton(boardGrid,i-1,j-1,board[i][j]);
//                else if (containsCoordinates(gameModelView.getSelectedCoordinates(), i, j))
//                    setNodeBoardSelected(boardGrid,i-1,j-1,board[i][j]);
//                else
//                    setNodeBoardTile(boardGrid,i-1,j-1,board[i][j]);
//            }
//        }
//        showMessage("It's "+ gameModelView.getCurrentPlayer() + "'s turn.\n");
//    }
//
//
//    private void setNodeBoardTile (GridPane gridPane,int row, int col,ItemTileType t){
//        if(t != ItemTileType.EMPTY) {
//            ImageView imageView = new ImageView();
//            imageView.setFitHeight(78);
//            imageView.setFitWidth(63);
//            imageView.setPreserveRatio(true);
//            Random random = new Random();
//            Integer ran = random.nextInt(4 - 1) + 1;
//            Image image = new Image(getClass().getResource("/view/gui/item_tiles/" + t + "1."+ ran +".png").toString());
//            imageView.setImage(image);
//            imageView.setOpacity(0.5);
//            gridPane.add(imageView,col,row);
//        }
//    }
//
//    private void setNodeBoardTileButton (GridPane gridPane,int row, int col,ItemTileType t){
//        ImageView imageView = new ImageView();
//        imageView.setFitHeight(78);
//        imageView.setFitWidth(63);
//        imageView.setPreserveRatio(true);
//        Random random = new Random();
//        Integer ran = random.nextInt(4 - 1) + 1;
//        Image image = new Image(getClass().getResource("/view/gui/item_tiles/" + t + "1."+ ran +".png").toString());
//        imageView.setImage(image);
//        Button button = new Button();
//        button.setPrefSize(63,78);
//        button.setGraphic(imageView);
//        gridPane.add(button,col,row);
//        buttons.add(button);
//    }
//
//    private void setNodeBoardSelected (GridPane gridPane,int row, int col,ItemTileType t){
//        ImageView imageView = new ImageView();
//        imageView.setFitHeight(78);
//        imageView.setFitWidth(63);
//        imageView.setPreserveRatio(true);
//        Random random = new Random();
//        Integer ran = random.nextInt(4 - 1) + 1;
//        Image image = new Image(getClass().getResource("/view/gui/item_tiles/" + t + "1."+ ran +".png").toString());
//        imageView.setImage(image);
//        gridPane.add(imageView,col,row);
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
//                            Integer ran = random.nextInt(3 - 1) + 1;
//                            Image image = new Image(getClass().getResource("/view/gui/item_tiles/" + t + "1."+ ran +".png").toString());
//                            imageView.setImage(image);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    public void selectCoordinates(GameModelView gameModelView){
//        showMessage("Do you want to select a coordinate from the board? yes/no");
//        askYesOrNo(gameModelView);
//    }
//    public void askYesOrNo(GameModelView gameModelView) {
//
//        Button yesButton = new Button();
//        yesButton.setPrefSize(63,78);
//        yesButton.setText("Yes");
//        boardGrid.add(yesButton,1,1);
//        Button noButton = new Button();
//        noButton.setPrefSize(63,78);
//        noButton.setText("No");
//        boardGrid.add(noButton,1,2);
//        yesButton.setOnAction(event -> {
//            showMessage("The Dotted spots on the board are the tiles that can be selected.");
//            printCanBeSelectedCoordinates(gameModelView);
//        });
//
//
//    }
//
//    public void printCanBeSelectedCoordinates(GameModelView gameModelView) {
//       showMessage("Select a tile from the board");
//        for ( Button  b : buttons){
//            b.setEffect(getInnerGlow(100));
//            b.setOnAction(event -> {
//                String coordinates = GridPane.getColumnIndex(b) + " " + GridPane.getRowIndex(b);
//                viewGUI.setCoordinates(coordinates);
//            });
//        }
//    }
//
//
//    private static boolean isNumeric(String str) {
//        try {
//            Integer.parseInt(str);
//            return true;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }
//
//    public InnerShadow getInnerGlow(int depth) {
//        InnerShadow innerGlow = new InnerShadow();
//        innerGlow.setOffsetY(5);
//        innerGlow.setOffsetX(5);
//        innerGlow.setColor(javafx.scene.paint.Color.YELLOW);
//        innerGlow.setWidth(depth);
//        innerGlow.setHeight(depth);
//        return innerGlow;
//    }
//
//    public void deselectCoordinates(GameModelView gameModelView) {
//        printSelectedTiles(gameModelView);
//        showMessage("Do you want to deselect coordinates? yes/no: ");
//        askYesOrNo(gameModelView);
//        //String coordinates = getCoordinates();
//        // setChangedAndNotifyObservers(new MessageEvent(DESELECT_COORDINATES, coordinates));
//        //else if  (gameModelView.getSelectedCoordinates().size() == 3 || gameModelView.getCanBeSelectedCoordinates().size() == 0)
//        //  viewGUI.pickTiles();
//        //else selectCoordinates(gameModelView);
//    }
//
//    public void printSelectedTiles(GameModelView gameModelView) {
//        showMessage("These are the tiles you have selected:");
//        for(int i = 0; i < gameModelView.getSelectedTiles().size();i++)
//            gameModelView.getSelectedTiles().get(i);
//    }
//
//}
//
