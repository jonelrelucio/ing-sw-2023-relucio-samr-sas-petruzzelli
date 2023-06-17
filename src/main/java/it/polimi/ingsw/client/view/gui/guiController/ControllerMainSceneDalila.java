package it.polimi.ingsw.client.view.gui.guiController;



import it.polimi.ingsw.client.view.gui.utils.Utils;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.*;

public class ControllerMainSceneDalila implements Initializable {

    @FXML
    Button yesButton;
    @FXML
    Button noButton;
    @FXML
    TextFlow textFlowGameMessages;
    Text text;
    Text textChat;
    @FXML
    GridPane boardGrid;
    @FXML
    Tab mainPlayerTab;
    @FXML
    GridPane personalBookshelfGrid;
    @FXML
    GridPane radioButtonChoiceGrid;
    @FXML
    RadioButton radioButtonChoice1;
    @FXML
    RadioButton radioButtonChoice2;
    @FXML
    RadioButton radioButtonChoice3;
    @FXML
    RadioButton radioButtonChoice4;
    @FXML
    RadioButton radioButtonChoice5;
    @FXML
    Rectangle personalRectangleChair;
    @FXML
    GridPane chosenTilesGrid;
    @FXML
    Button chosenTilesButton1;
    @FXML
    Button chosenTilesButton2;
    @FXML
    Button chosenTilesButton3;
    @FXML
    ImageView chosenTilesButton1Image;
    @FXML
    ImageView chosenTilesButton2Image;
    @FXML
    ImageView chosenTilesButton3Image;
    @FXML
    Rectangle personalRectangleFirstFullShelfWinner;
    @FXML
    ImageView imageComGoal1Square;
    @FXML
    ImageView imageComGoal1SquarePoint;
    @FXML
    Label labelComGoal1;
    @FXML
    ImageView imageComGoal2Square;
    @FXML
    ImageView imageComGoal2SquarePoint;
    @FXML
    Label labelComGoal2;
    @FXML
    ImageView imageFirstShelfWinner;
    @FXML
    Label labelFirstShelfWinner;
    @FXML
    TabPane playersFullTab;
    @FXML
    Tab player1Tab;
    @FXML
    Tab player2Tab;
    @FXML
    Tab player3Tab;
    @FXML
    GridPane bookShelfGridPlayer1;
    @FXML
    Rectangle rectangleChairPlayer1;
    @FXML
    Rectangle player1RectangleFirstFullShelfWinner;
    @FXML
    GridPane bookShelfGridPlayer2;
    @FXML
    Rectangle rectangleChairPlayer2;
    @FXML
    Rectangle player2RectangleFirstFullShelfWinner;
    @FXML
    GridPane bookShelfGridPlayer3;
    @FXML
    Rectangle rectangleChairPlayer3;
    @FXML
    Rectangle player3RectangleFirstFullShelfWinner;
    @FXML
    Image image;
    @FXML
    TextFlow chatTextFlow1;

    private  ArrayList<Button> buttons;

    private  ArrayList<Button> chosenTilesButtons;

    private  ArrayList<Button> allChosenTilesButtons;
    private ImageView[][] cells;
    private ToggleGroup columnToggleGroup;

    private static ViewGui viewGUI;
    private static final int height = 40;
    private static final int width = 35;

    private HashMap<Integer, String> commonGoalCardDescriptions;




    public void setViewGui(ViewGui viewGUI) {
        ControllerMainSceneDalila.viewGUI = viewGUI;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showMessage("Il gioco è iniziato");
        columnToggleGroup = new ToggleGroup();
        this.radioButtonChoice1.setToggleGroup(columnToggleGroup);
        this.radioButtonChoice2.setToggleGroup(columnToggleGroup);
        this.radioButtonChoice3.setToggleGroup(columnToggleGroup);
        this.radioButtonChoice4.setToggleGroup(columnToggleGroup);
        this.radioButtonChoice5.setToggleGroup(columnToggleGroup);
        radioButtonChoiceGrid.setVisible(false);
        chosenTilesGrid.setVisible(false);
        allChosenTilesButtons = new ArrayList<>();
        allChosenTilesButtons.add(chosenTilesButton1);
        allChosenTilesButtons.add(chosenTilesButton2);
        allChosenTilesButtons.add(chosenTilesButton3);
        rectangleChairPlayer1.setVisible(false);
        rectangleChairPlayer2.setVisible(false);
        rectangleChairPlayer3.setVisible(false);
        personalRectangleChair.setVisible(false);
        personalRectangleFirstFullShelfWinner.setVisible(false);
        player1RectangleFirstFullShelfWinner.setVisible(false);
        player2RectangleFirstFullShelfWinner.setVisible(false);
        player3RectangleFirstFullShelfWinner.setVisible(false);
        image = new Image(getClass().getResource("/view/gui/scoring_tokens/end_game.jpg").toString());
        imageFirstShelfWinner.setImage(image);
        labelFirstShelfWinner.setText("The first player who completely fills\ntheir bookshelf will obtain this token\nand will score 1 additional points");
        hideYesOrNo();
        commonGoalCardDescriptions = new HashMap<>();
        initCommonGoalCardDescription();
    }

    private void initCommonGoalCardDescription() {
        commonGoalCardDescriptions.put(1, Utils.desc1);
        commonGoalCardDescriptions.put(2, Utils.desc2);
        commonGoalCardDescriptions.put(3, Utils.desc3);
        commonGoalCardDescriptions.put(4, Utils.desc4);
        commonGoalCardDescriptions.put(5, Utils.desc5);
        commonGoalCardDescriptions.put(6, Utils.desc6);
        commonGoalCardDescriptions.put(7, Utils.desc7);
        commonGoalCardDescriptions.put(8, Utils.desc8);
        commonGoalCardDescriptions.put(9, Utils.desc9);
        commonGoalCardDescriptions.put(10, Utils.desc10);
        commonGoalCardDescriptions.put(11, Utils.desc11);
        commonGoalCardDescriptions.put(12, Utils.desc12);
    }



    public void setFirstPlayerChair(Rectangle r){
        image = new Image(getClass().getResource("/view/gui/background/firstPlayerToken.jpg").toString());
        r.setFill(new ImagePattern(image));
        r.setVisible(true);

    };
    public void showMessage(String s){
        clearMessage();
        text = new Text(s + "\n");
        textFlowGameMessages.getChildren().add(text);
        textChat = new Text(s + "\n");
        chatTextFlow1.getChildren().add(textChat);
    }

    public void clearMessage(){
        textFlowGameMessages.getChildren().clear();
    }

    private boolean containsCoordinates(ArrayList<int[]> list, int i, int j){
        int[] coordinates = new int[]{i, j};
        for (int[] array : list) {
            if (Arrays.equals(array, coordinates)) return true;
        }
        return false;
    }


    public void showBoard(GameModelView gameModelView){
        showMessage("trying to print");
        clearBoard();
        ItemTileType[][] board = gameModelView.getBoardMatrix();
        buttons = new ArrayList<>();
        for (int i = 1; i < board.length-1; i++){
            for (int j = 1; j < board.length-1; j++) {
                if (containsCoordinates( gameModelView.getCanBeSelectedCoordinates(), i, j))
                    setNodeBoardTileButton(boardGrid,i-1,j-1, board[i][j]);
                else if (containsCoordinates(gameModelView.getSelectedCoordinates(), i, j))
                    boardGrid.add(createImageTile(board[i][j],false),j-1,i-1);
                else if( board[i][j] != ItemTileType.EMPTY)
                    boardGrid.add(createImageTile(board[i][j],true),j-1,i-1);
            }
        }
        showMessage("It's "+ gameModelView.getCurrentPlayer() + "'s turn.\n");
    }




    private void setNodeBoardTileButton (GridPane gridPane,int row, int col,ItemTileType t){
        Button button = new Button();
        button.setPrefSize(height,width);
        button.setGraphic(createImageTile(t,false));
        gridPane.add(button,col,row);
        buttons.add(button);
    }

    public void showTabPlayers(String [] players){
        if(players.length==2){
            playersFullTab.getTabs().remove(player2Tab);
            playersFullTab.getTabs().remove(player3Tab);
        } else if (players.length==3) {
            playersFullTab.getTabs().remove(player3Tab);
        }
    }
    public void showBookshelves(GameModelView gameModelView) {
        String[] players = gameModelView.getPlayerList();
        showTabPlayers(players);
        for (int i = 0; i < players.length; i++) {
            if(players[i].equals(viewGUI.getThisUsername())){
                setDetailesPlayer(mainPlayerTab, personalBookshelfGrid, players[i],gameModelView.getBookshelfList()[i]);
            } else {
                setDetailesPlayer(player1Tab,bookShelfGridPlayer1, players[i],gameModelView.getBookshelfList()[i]);
            }
        }
        if(players[0].equals(viewGUI.getThisUsername())){
            setFirstPlayerChair(personalRectangleChair);
        } else {
            setFirstPlayerChair(rectangleChairPlayer1);
        }
    }

    public void setDetailesPlayer(Tab t,GridPane g, String name, ItemTileType [][] bookShelf){
        t.setText(name);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if(bookShelf[i][j] != ItemTileType.EMPTY) {
                    g.add(createImageTile(bookShelf[i][j],false),j,i);
                }
            }
        }
    }

    public void selectCoordinates(GameModelView gameModelView,String s){
        showMessage(s);
        askYesOrNo();
        for (int [] i: gameModelView.getCanBeSelectedCoordinates()) {
            showMessage(i[0]+ " " + i[1]);
        }
        yesButton.setOnAction(event -> {
            hideYesOrNo();
            showMessage("The Glowing tiles are the one that can be selected");
            printCanBeSelectedCoordinates();

        });
        noButton.setOnAction(event -> {
            if(gameModelView.getSelectedTiles().size() == 0){
                showMessage("You have not selected a tile. Please select at least a tile.\nThe Glowing tiles are the one that can be selected");
            }else{
                viewGUI.pickTiles();
            }
        });
    }
    public void askYesOrNo() {
        yesButton.setVisible(true);
        noButton.setVisible(true);
    }

    public void hideYesOrNo() {
        yesButton.setVisible(false);
        noButton.setVisible(false);
    }

    public void printCanBeSelectedCoordinates() {
        showMessage("Select a tile from the board");
        for ( Button b : buttons){
            b.setEffect(getInnerGlow(50));
            b.setOnAction(event -> {
                int [] coor = new int[2];
                coor [0] = GridPane.getRowIndex(b)==null ? 1 : GridPane.getRowIndex(b)+1;
                coor [1] = GridPane.getColumnIndex(b) == null ? 1 : GridPane.getColumnIndex(b)+1;
                showMessage("Coordinata scelta:" + coor[0] + " "+ coor[1]);
                String coordinates = getCoordinates(coor);
                showMessage(coordinates);
                viewGUI.setCoordinates(coordinates);
            });
        }
    }


    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public InnerShadow getInnerGlow(int depth) {
        InnerShadow innerGlow = new InnerShadow();
        innerGlow.setOffsetY(5);
        innerGlow.setOffsetX(5);
        innerGlow.setColor(javafx.scene.paint.Color.YELLOW);
        innerGlow.setWidth(depth);
        innerGlow.setHeight(depth);
        return innerGlow;
    }

    public void deselectCoordinates(GameModelView gameModelView) {
        printSelectedTiles(gameModelView);
        showMessage("Do you want to deselect coordinates?");
        askYesOrNo();
        yesButton.setOnAction(event -> {
            printSelectedTiles(gameModelView);
        });
        noButton.setOnAction(event -> {
            //if (gameModelView.getSelectedCoordinates().size() == 3 || gameModelView.getCanBeSelectedCoordinates().size() == 0) {
            //hideYesOrNo();
            viewGUI.pickTiles();
            //} else {
            //hideYesOrNo();
            //selectCoordinates(gameModelView,"Do you want to deselect coordinates?");
            //}
        });

    }

    public void printSelectedTiles(GameModelView gameModelView) {
        showMessage("These are the tiles you have selected:");
        hideYesOrNo();
        chosenTilesGrid.setVisible(true);
        for (int i = 0; i < gameModelView.getSelectedTiles().size(); i++) {
            ItemTileType t = gameModelView.getSelectedTiles().get(i);
            if (i == 0) {
                insertImageTile(chosenTilesButton1Image,t);
                chosenTilesButton1.setOnAction(event -> {
                    String coordinates = getCoordinates(gameModelView.getSelectedCoordinates().get(GridPane.getColumnIndex(chosenTilesButton1)));
                    viewGUI.setDeselectedCoordinates(coordinates);
                });
            } else if (i == 1) {
                insertImageTile(chosenTilesButton2Image,t);
                chosenTilesButton2.setOnAction(event -> {
                    String coordinates = getCoordinates(gameModelView.getSelectedCoordinates().get(GridPane.getColumnIndex(chosenTilesButton2)));
                    viewGUI.setDeselectedCoordinates(coordinates);
                });
            } else {
                insertImageTile(chosenTilesButton3Image,t);
                chosenTilesButton3.setOnAction(event -> {
                    String coordinates = getCoordinates(gameModelView.getSelectedCoordinates().get(GridPane.getColumnIndex(chosenTilesButton3)));
                    viewGUI.setDeselectedCoordinates(coordinates);
                });
            }

        }

    }

    private void insertImageTile(ImageView im,ItemTileType t){
        Random random = new Random();
        int ran = random.nextInt(4 - 1) + 1;
        image = new Image(getClass().getResource("/view/gui/item_tiles/" + t + "1."+ ran +".png").toString());
        im.setImage(image);
    }

    private ImageView createImageTile(ItemTileType t,Boolean b){
        ImageView imageView = new ImageView();
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);
        insertImageTile(imageView,t);
        if(b)
            imageView.setOpacity(0.5);
        return imageView;
    }

    private String getCoordinates(int [] c){
        String input = String.format("%d",c[0]) + " " + String.format("%d",c[1]);
        int x, y;
        String[] coordinates = input.split(" ");
        if (coordinates.length != 2 || !isNumeric(coordinates[0]) || !isNumeric(coordinates[1])) {
            showMessage("Invalid coordinates, try again..." + input + "\n");
        }
        x = Integer.parseInt(coordinates[0]);
        y = Integer.parseInt(coordinates[1]);
        if(x<0 || y<0) {
            showMessage("x or y less than 0 => " + input + "\n");
        }
        return input;
    }


    public void showColumnToggle() {
        showMessage("select a radio button on top of the column where you want to put your tiles.\n");
        radioButtonChoiceGrid.setVisible(true);
        if (this.columnToggleGroup.getSelectedToggle().equals(radioButtonChoice1)) {
            showMessage("The column chosen is 1\n Confirm choice by selecting yes or choose another");
            setColumn("0");
        }
        if (this.columnToggleGroup.getSelectedToggle().equals(radioButtonChoice2)) {
            showMessage("The column chosen is 2\n Confirm choice by selecting yes or choose another");
            setColumn("1");
        }
        if (this.columnToggleGroup.getSelectedToggle().equals(radioButtonChoice3)) {
            showMessage("The column chosen is 3\n Confirm choice by selecting yes or choose another");
            setColumn("2");
        }
        if (this.columnToggleGroup.getSelectedToggle().equals(radioButtonChoice4)) {
            showMessage("The column chosen is 4\n Confirm choice by selecting yes or choose another");
            setColumn("3");
        }
        if (this.columnToggleGroup.getSelectedToggle().equals(radioButtonChoice5)) {
            showMessage("The column chosen is 5\n Confirm choice by selecting yes or choose another");
            setColumn("4");
        }
    }

    private void setColumn(String s) {
        yesButton.setVisible(true);
        noButton.setVisible(false);
        yesButton.setOnAction(event -> viewGUI.setSelectedColumn(s));
    }

    public void clearBoard() {
        if(!boardGrid.getChildren().isEmpty())
            boardGrid.getChildren().clear();
    }

    public void showSelectedTiles(GameModelView gameModelView) {
        showMessage("in Your tab are the tiles you have selected");
        chosenTilesGrid.setVisible(true);
        int size = gameModelView.getSelectedTiles().size();
        chosenTilesButtons = new ArrayList<>();
        boolean bool = size > 0;
        size--;
        setSelectedTile(chosenTilesButton1Image,gameModelView.getSelectedTiles().get(0),chosenTilesButton1,bool);
        bool = size > 0;
        size--;
        setSelectedTile(chosenTilesButton2Image,gameModelView.getSelectedTiles().get(0),chosenTilesButton2,bool);
        bool = size > 0;
        setSelectedTile(chosenTilesButton3Image,gameModelView.getSelectedTiles().get(0),chosenTilesButton3,bool);

    }
    private void setSelectedTile(ImageView im, ItemTileType t, Button b, Boolean cond){
        if(cond){
            chosenTilesButtons.add(b);
            insertImageTile(im,t);
        }else{
            b.setVisible(false);
        }
    }

    public void askTileOrder(GameModelView gameModelView) {
        showMessage("Do you want to order your Tiles?");
        askYesOrNo();
        yesButton.setOnAction(event -> {
            showMessage("Rearrange the tiles by setting a new array of indexes:\n(example of new order: 2 0 1)");
            int [] order = new int[chosenTilesButtons.size()];
            arrangeOrder(gameModelView,order);
        });
        noButton.setOnAction(event -> {
            viewGUI.selectColumn(gameModelView);
        });

    }

    public void arrangeOrder(GameModelView gameModelView,int [] order){
        chosenTilesGrid.setVisible(true);
        for (Button b: chosenTilesButtons) {
            b.setOnAction(event -> {
                int i = order.length - chosenTilesButtons.size() + 1;
                b.setText(String.valueOf(i));
                b.setEffect(getInnerGlow(50));
                order[i-1] = GridPane.getColumnIndex(b);
                chosenTilesButtons.remove(b);
            });
        }
        if(chosenTilesButtons.isEmpty()){
            showMessage("Are you satisfied with your order?");
            askYesOrNo();
            yesButton.setOnAction(event -> {
                String input = getTileOrder(order);
                hideYesOrNo();
                viewGUI.setNewOrder(input);
            });
            noButton.setOnAction(event -> {
                for (Button b: allChosenTilesButtons) {
                    if(b.isVisible()) chosenTilesButtons.add(b);
                }
                askTileOrder(gameModelView);
            });
        }
    }
    private String getTileOrder(int [] intArr) {
        String input = null;
        int i = 0;
        while( i < intArr.length -  1) {
            input = intArr[i] + " ";
            i++;
        }
        input = input + intArr[i];
        return input;
    }

    public void showPersonalGoal(GameModelView gameModelView) {
        ItemTileType[][] personalGoalCard = gameModelView.getPersonalGoalCardList().get(viewGUI.getThisUsername());

    }

    public void showCommonGoalCard(GameModelView gameModelView) {
        HashMap<Integer, Integer[]> commonGoalCardDeck = gameModelView.getCommonGoalCardDeck();
        int i = 0;
        for (Map.Entry<Integer, Integer[]> set : commonGoalCardDeck.entrySet()) {
            if(i == 0){
                initCommonGoalCard(set, imageComGoal1SquarePoint, imageComGoal1Square, labelComGoal1);
            }else{
                initCommonGoalCard(set, imageComGoal2SquarePoint, imageComGoal2Square, labelComGoal2);
            }
            i++;
        }
    }

    public void initCommonGoalCard(Map.Entry<Integer, Integer[]> set, ImageView imageComGoalSquarePoint, ImageView imageComGoalSquare, Label labelComGoal) {
        String description;
        for (Integer point : set.getValue()) {
            image = new Image(getClass().getResource("/view/gui/scoring_tokens/scoring_"+point+".jpg").toString());
            imageComGoalSquarePoint.setImage(image);
        }
        image = new Image(getClass().getResource("/view/gui/common_goal_cards/"+set.getKey()+".jpg").toString());
        imageComGoalSquare.setImage(image);
        description = commonGoalCardDescriptions.get(set.getKey());
        labelComGoal.setText(description);
    }


}

