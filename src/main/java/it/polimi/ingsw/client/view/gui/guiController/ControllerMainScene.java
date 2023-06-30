package it.polimi.ingsw.client.view.gui.guiController;



import it.polimi.ingsw.client.view.gui.utils.Utils;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.*;

public class ControllerMainScene implements Initializable {

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
    ImageView personalGoalImage;
    @FXML
    Button personalGoalButton;
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
    Label chosenTilesButton1Label;
    @FXML
    Label chosenTilesButton2Label;
    @FXML
    Label chosenTilesButton3Label;
    @FXML
    Rectangle personalRectangleFirstFullShelfWinner;
    @FXML
    Label personalScoreLabel;
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
    TextFlow chatTextFlow;
    @FXML
    ChoiceBox playersListChat;
    @FXML
    Label choiceBoxLabel;
    @FXML
    Button chatButtonSend;
    @FXML
    TextField chatTextField;
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
    Label player1ScoreLabel;
    @FXML
    GridPane bookShelfGridPlayer2;
    @FXML
    Rectangle rectangleChairPlayer2;
    @FXML
    Rectangle player2RectangleFirstFullShelfWinner;
    @FXML
    Label player2ScoreLabel;
    @FXML
    GridPane bookShelfGridPlayer3;
    @FXML
    Rectangle rectangleChairPlayer3;
    @FXML
    Rectangle player3RectangleFirstFullShelfWinner;
    @FXML
    Label player3ScoreLabel;
    @FXML
    StackPane chosenTilesStackPane1;
    @FXML
    StackPane chosenTilesStackPane2;
    @FXML
    StackPane chosenTilesStackPane3;
    @FXML
    Image image;


    private  ArrayList<Button> buttons;

    private  ArrayList<Button> chosenTilesButtons;

    private  ArrayList<Label> chosenTilesLabels;

    private  ArrayList<Button> allChosenTilesButtons;
    private ToggleGroup columnToggleGroup;

    private static ViewGui viewGUI;
    private static final int height = 40;
    private static final int width = 35;
    private static Boolean clicked;
    private final String[] chatColors = new String[]{"blue","orange","green"};

    private HashMap<Integer, String> commonGoalCardDescriptions;

    private String lastPrivateMessage = null;


    /**
     * Set the field 'viewGUI'
     * @param viewGUI
     */
    public void setViewGui(ViewGui viewGUI) {
        ControllerMainScene.viewGUI = viewGUI;
    }

    /**
     *Initializes the main game scene.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        chatTextField.setText("Write your message here");
        choiceBoxLabel.setText("");
        commonGoalCardDescriptions = new HashMap<>();
        initCommonGoalCardDescription();
        initLabelPersonalTiles();
        imageComGoal1Square.setCache(false);
        imageComGoal1SquarePoint.setCache(false);
        imageComGoal2Square.setCache(false);
        imageComGoal2SquarePoint.setCache(false);
        personalGoalImage.setCache(false);
        chosenTilesButton1Image.setCache(false);
        chosenTilesButton2Image.setCache(false);
        chosenTilesButton3Image.setCache(false);
        imageFirstShelfWinner.setCache(false);
        textFlowGameMessages.setCache(false);
        clicked = false;
        chatTextField.clear();
    }

    /**
     *Initializes the label that show the order of the tiles in the main player tab.
     */
    private void initLabelPersonalTiles() {
        chosenTilesButton1Label.setText("");
        chosenTilesButton3Label.setText("");
        chosenTilesButton2Label.setText("");
    }
    /**
     * Fills the 'commonGoalCardDescriptions' HashMap with all the cards id and their description
     */
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


    /**
     *Set's the rectangle 'r' with the image of the first player token in the common goal card tab.
     * @param r
     */
    public void setFirstPlayerChair(Rectangle r){
        image = new Image(getClass().getResource("/view/gui/background/firstplayertoken.jpg").toString());
        r.setFill(new ImagePattern(image));
        r.setVisible(true);

    };

    /**
     * Adds the new message in the chat tab's text field.
     * @param s message text content
     * @param c message text color
     */
    public void showMessage(String s,String c){
        textChat = new Text(s + "\n");
        textChat.setFill(Color.valueOf(c));
        chatTextFlow.getChildren().add(textChat);
    }

    /**
     * Show the new flow message in Game Messages' text field guiding the user's action.
     * @param s message text content
     * @see #clearMessage()
     */
    public void showGameMessage(String s){
        clearMessage();
        text = new Text(s + "\n");
        text.setFont(Font.font("Century Schoolbook", FontWeight.NORMAL,20));
        textFlowGameMessages.getChildren().add(text);
    }

    /**
     * Clears the Game Messages' text field of the previous flow message
     */
    public void clearMessage(){
        textFlowGameMessages.getChildren().clear();
    }

    /**
     * Check if the 'list' parameter contains the coordinates of {i, j}
     * @param list
     * @param i
     * @param j
     * @return true if yes, otherwise false
     */
    private boolean containsCoordinates(ArrayList<int[]> list, int i, int j){
        int[] coordinates = new int[]{i, j};
        for (int[] array : list) {
            if (Arrays.equals(array, coordinates)) return true;
        }
        return false;
    }

    /**
     * After the send button is clicked call's the viewGUI method tha will notify the server of the new message or the new private message
     * #see ShowMessage(String,Color)
     */
    public void setChatButtonSend(){
        if(!chatTextField.getText().isEmpty()) {
            if(playersListChat.getValue().equals("All")){
            viewGUI.setNewMessage(chatTextField.getText());
            chatTextField.clear();
            }else{
                viewGUI.setNewPrivateMessage((String) playersListChat.getValue(),chatTextField.getText());
                if (!playersListChat.getValue().equals(viewGUI.getThisUsername()))
                    showMessage("(private) From "+ viewGUI.getThisUsername() +  " to " + playersListChat.getValue() + ": "+ chatTextField.getText(), "black");
                chatTextField.clear();}
        }
    }

    /**
     * Fill's the choice Box with all the name of the players and th 'All' option.
     */
    public void initChat(String[] players){
        playersListChat.getItems().clear();
        ObservableList<String> listPlayers = FXCollections.observableArrayList();
        listPlayers.addAll(players);
        playersListChat.setItems(listPlayers);
        playersListChat.getItems().add("All");
        playersListChat.setValue("All");
    }


    /**
     * Fill's the board game with the item tiles adding buttons to the clickable ones
     * @param gameModelView
     * @see #containsCoordinates(ArrayList, int, int)
     * @see #setNodeBoardTileButton(GridPane, int, int, ItemTileType, int)
     */
    public void showBoard(GameModelView gameModelView){
        clearBoard();
        ItemTileType[][] board = gameModelView.getBoardMatrix();
        int[][] boardId = gameModelView.getBoardItemId();
        buttons = new ArrayList<>();
        for (int i = 1; i < board.length-1; i++){
            for (int j = 1; j < board.length-1; j++) {
                if (containsCoordinates( gameModelView.getCanBeSelectedCoordinates(), i, j))
                    setNodeBoardTileButton(boardGrid,i-1,j-1, board[i][j],boardId[i][j]);
                else if (containsCoordinates(gameModelView.getSelectedCoordinates(), i, j))
                    boardGrid.add(createImageTile(board[i][j],boardId[i][j],false),j-1,i-1);
                else if( board[i][j] != ItemTileType.EMPTY)
                    boardGrid.add(createImageTile(board[i][j],boardId[i][j],true),j-1,i-1);
            }
        }
    }

    /**
     * Adds buttons to the Grid Pane
     * @param gridPane
     * @param row
     * @param col
     * @param t
     * @param id
     * @see #createImageTile(ItemTileType, int, Boolean)
     */
    private void setNodeBoardTileButton (GridPane gridPane,int row, int col,ItemTileType t,int id){
        Button button = new Button();
        button.setPrefSize(height,width);
        button.setGraphic(createImageTile(t,id,false));
        button.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        gridPane.add(button,col,row);
        buttons.add(button);
    }

    /***
     * removes player's tab based on number of players in the game
     * @param players
     */
    public void showTabPlayers(String [] players){
        if(players.length==2){
            playersFullTab.getTabs().remove(player2Tab);
            playersFullTab.getTabs().remove(player3Tab);
        } else if (players.length==3) {
            playersFullTab.getTabs().remove(player3Tab);
        }
    }

    /**
     * Fill's the palyer's tab with all the correspondent information
     * @param gameModelView
     * @see #setDetailesPlayer(Tab, GridPane, Label, int, String, ItemTileType[][], int[][])
     * @see #setFirstPlayerChair(Rectangle)
     */
    public void showBookshelves(GameModelView gameModelView) {
        String[] players = gameModelView.getPlayerList();
        showTabPlayers(players);
        int j = 0;
        for (int i = 0; i < players.length; i++) {
            if(players[i].equals(viewGUI.getThisUsername())){
                setDetailesPlayer(mainPlayerTab, personalBookshelfGrid, personalScoreLabel, gameModelView.getPointsList()[i], players[i],gameModelView.getBookshelfList()[i],gameModelView.getBookshelfListItemId()[i]);
            } else if(j == 0) {
                j++;
                setDetailesPlayer(player1Tab,bookShelfGridPlayer1, player1ScoreLabel, gameModelView.getPointsList()[i],players[i],gameModelView.getBookshelfList()[i],gameModelView.getBookshelfListItemId()[i]);
            }else if(j== 1) {
                j++;
                setDetailesPlayer(player2Tab,bookShelfGridPlayer2,player2ScoreLabel,gameModelView.getPointsList()[i], players[i],gameModelView.getBookshelfList()[i],gameModelView.getBookshelfListItemId()[i]);
            }else if(j == 2){
                setDetailesPlayer(player3Tab,bookShelfGridPlayer3, player3ScoreLabel,gameModelView.getPointsList()[i], players[i],gameModelView.getBookshelfList()[i],gameModelView.getBookshelfListItemId()[i]);
            }
        }
        if(players[0].equals(viewGUI.getThisUsername())){
            setFirstPlayerChair(personalRectangleChair);
        } else {
            setFirstPlayerChair(rectangleChairPlayer1);
        }
    }

    /**
     * Fill's the bookshelf's grid pane in the players' tabs with the correspondent item tiles
     * @param bookShelf
     * @param bookShelfId
     * @param g
     * @param name
     * @param score
     * @param t
     * @param scoreLabel
     * @see #clearBookShelf(GridPane)
     */
    public void setDetailesPlayer(Tab t,GridPane g, Label scoreLabel, int score, String name, ItemTileType [][] bookShelf,int [][] bookShelfId){
        t.setText(name);
        scoreLabel.setText("Score: " + score);
        clearBookShelf(g);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if(bookShelf[i][j] != ItemTileType.EMPTY) {
                    g.add(createImageTile(bookShelf[i][j],bookShelfId[i][j],false),j,i);
                }
            }
        }
    }
    /**
     * Asks the player if he wants to select an item tile from board and sets action to yes/no buttons.
     * @param gameModelView
     * @param s
     * @see #printCanBeSelectedCoordinates()
     * @see #askYesOrNo()
     * @see #showGameMessage(String)
     * @see #hideYesOrNo()
     */
    public void selectCoordinates(GameModelView gameModelView,String s){
        showGameMessage(s);
        askYesOrNo();
        yesButton.setOnAction(event -> {
            hideYesOrNo();
            showGameMessage("Please Select a Tile.\nThe Glowing tiles are the one that can be selected");
            printCanBeSelectedCoordinates();
        });
        noButton.setOnAction(event -> {
            if(gameModelView.getSelectedCoordinates().size() == 0){
                hideYesOrNo();
                showGameMessage("You have not selected a tile.\nPlease select at least a tile.\nThe Glowing tiles are the one that can be selected");
                printCanBeSelectedCoordinates();
            }else{
                hideYesOrNo();
                viewGUI.pickTiles();
            }
        });
    }
    /**
     * sets visible the yes and no button
     *
     */
    public void askYesOrNo() {
        yesButton.setVisible(true);
        noButton.setVisible(true);
    }
    /**
     *hides  the yes and no button
     *
     */
    public void hideYesOrNo() {
        yesButton.setVisible(false);
        noButton.setVisible(false);
    }
    /**
     * Adds action to the button with the item tiles that can be selected highlighting them with yellow
     * @see #setInnerGlow(int)
     * @see #getCoordinates(int[])
     */

    public void printCanBeSelectedCoordinates() {
        for ( Button b : buttons){
            b.setEffect(setInnerGlow(50));
            b.setOnAction(event -> {
                int [] coor = new int[2];
                coor [0] = GridPane.getRowIndex(b)==null ? 1 : GridPane.getRowIndex(b)+1;
                coor [1] = GridPane.getColumnIndex(b) == null ? 1 : GridPane.getColumnIndex(b)+1;
                String coordinates = getCoordinates(coor);
                viewGUI.setCoordinates(coordinates);
            });
        }
    }

    /**
     * Check if the parameter 'str' is a number
     * @param str
     * @return true if 'str' is a number, else false
     */
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /**
     *Returns the inner shadow that gives the effect of yellow glowing.
     * @param depth
     * @return
     */
    public InnerShadow setInnerGlow(int depth) {
        InnerShadow innerGlow = new InnerShadow();
        innerGlow.setOffsetY(5);
        innerGlow.setOffsetX(5);
        innerGlow.setColor(javafx.scene.paint.Color.YELLOW);
        innerGlow.setWidth(depth);
        innerGlow.setHeight(depth);
        return innerGlow;
    }
    /**
     * Asks the player if he wants to deselect an item tile from grid pane in tab and sets action to yes/no buttons.
     * @param gameModelView
     * @see #printSelectedTiles(GameModelView)
     * @see #askYesOrNo()
     * @see #selectCoordinates(GameModelView, String)
     */
    public void deselectCoordinates(GameModelView gameModelView) {
        showGameMessage("Do you want to deselect coordinates?");
        askYesOrNo();
        yesButton.setOnAction(event -> {
            printSelectedTiles(gameModelView);
        });
        noButton.setOnAction(event -> {
            if (gameModelView.getSelectedCoordinates().size() == 3 || gameModelView.getCanBeSelectedCoordinates().size() == 0) {
                viewGUI.pickTiles();
            } else {
                selectCoordinates(gameModelView,"Do you want to select a coordinate from the board?");
            }
        });

    }

    /**
     * Ask the player to choose if he wants to remove a coordinate or not from the chosen ones.
     * Hows the selected tiles in the personal tab.
     * @param gameModelView
     * @see #setSelectedTilesPane(GameModelView, ItemTileType, int, Button, ImageView, Label, StackPane)
     * @see #showGameMessage(String)
     * @see #hideYesOrNo()
     * @see #hideSelectedTilesPanes()
     */
    public void printSelectedTiles(GameModelView gameModelView) {
        showGameMessage("In your tab will be shown the tiles you have selected.\nPlease select the one that you want to remove");
        hideYesOrNo();
        chosenTilesGrid.setVisible(true);
        hideSelectedTilesPanes();
        for (int i = 0; i < gameModelView.getSelectedCoordinates().size(); i++) {
            ItemTileType[][] board = gameModelView.getBoardMatrix();
            int [][] boardId = gameModelView.getBoardItemId();
            ItemTileType t = board[gameModelView.getSelectedCoordinates().get(i)[0]][gameModelView.getSelectedCoordinates().get(i)[1]];
            int id = boardId[gameModelView.getSelectedCoordinates().get(i)[0]][gameModelView.getSelectedCoordinates().get(i)[1]];
            if (i == 0) {
                setSelectedTilesPane(gameModelView, t, id, chosenTilesButton1, chosenTilesButton1Image, chosenTilesButton1Label,chosenTilesStackPane1);
            } else if (i == 1) {
                setSelectedTilesPane(gameModelView, t, id, chosenTilesButton2, chosenTilesButton2Image, chosenTilesButton2Label,chosenTilesStackPane2);
            } else {
                setSelectedTilesPane(gameModelView, t, id, chosenTilesButton3, chosenTilesButton3Image, chosenTilesButton3Label,chosenTilesStackPane3);
            }
        }
    }
    /**
     * Fill's the selected tiles' grid pane in the player's tab with the correspondent item tiles and sets the set on action buttons
     * @param gameModelView
     * @param id 
     * @param sp 
     * @param chosenTilesButton 
     * @param chosenTilesImage
     * @param t 
     * @param chosenTilesLabel 
     * @see #getCoordinates(int[]) 
     * @see #insertImageTile(ImageView, ItemTileType, int) 
     */
    public void setSelectedTilesPane(GameModelView gameModelView, ItemTileType t, int id, Button chosenTilesButton, ImageView chosenTilesImage, Label chosenTilesLabel,StackPane sp) {
        sp.setVisible(true);
        chosenTilesButton.setDisable(false);
        insertImageTile(chosenTilesImage,t,id);
        chosenTilesLabel.setText("");
        chosenTilesButton.setOnAction(event -> {
            String coordinates = getCoordinates(gameModelView.getSelectedCoordinates().get(GridPane.getColumnIndex(chosenTilesButton)));
            viewGUI.setDeselectedCoordinates(coordinates);
            chosenTilesGrid.setVisible(false);
        });
    }
    /**
     * Hides the stack panes in the player tab
     */
    private void hideSelectedTilesPanes() {
        chosenTilesStackPane1.setVisible(false);
        chosenTilesStackPane2.setVisible(false);
        chosenTilesStackPane3.setVisible(false);
    }
    /**
     * Set the image to the correspondent image view
     * @param t 
     * @param id 
     * @param im 
     */
    private void insertImageTile(ImageView im,ItemTileType t,int id){
        image = new Image(getClass().getResource("/view/gui/item_tiles/" + t + "1."+ id +".png").toString());
        im.setImage(image);
    }
    /**
     * Returns an image view
     * @param id
     * @param t
     * @param b
     * @see #insertImageTile(ImageView, ItemTileType, int)
     */
    private ImageView createImageTile(ItemTileType t,int id,Boolean b){
        ImageView imageView = new ImageView();
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);
        insertImageTile(imageView,t,id);
        if(b)
            imageView.setOpacity(0.5);
        return imageView;
    }

    /**
     * Checks the coordinates and returns a string with the correspondent coordinates
     * @param c
     * @return the coordinates inserted by the user as a String
     * @see #showGameMessage(String)
     */
    private String getCoordinates(int [] c){
        String input = String.format("%d",c[0]) + " " + String.format("%d",c[1]);
        int x, y;
        String[] coordinates = input.split(" ");
        if (coordinates.length != 2 || !isNumeric(coordinates[0]) || !isNumeric(coordinates[1])) {
            showGameMessage("Invalid coordinates, try again..." + input + "\n");
        }
        x = Integer.parseInt(coordinates[0]);
        y = Integer.parseInt(coordinates[1]);
        if(x<0 || y<0) {
            showGameMessage("x or y less than 0 => " + input + "\n");
        }
        return input;
    }


    /**
     *Shows the column toggle group in the players tab
     * @see #hideYesOrNo()
     * @see #showGameMessage(String)
     */
    public void showColumnToggle() {
        showGameMessage("select a radio button on top of the column where you want to put your tiles.\n");
        radioButtonChoiceGrid.setVisible(true);
        hideYesOrNo();
    }

    /**
     *Sets the column toggle group action in the players tab
     * @see #showGameMessage(String)
     * @see #setColumn(String, RadioButton)
     */
    public void setColumnToggle(){
        if (this.columnToggleGroup.getSelectedToggle().equals(radioButtonChoice1)) {
            showGameMessage("The column chosen is 1\n Confirm choice by selecting yes or choose another");
            setColumn("0",radioButtonChoice1);
        }
        if (this.columnToggleGroup.getSelectedToggle().equals(radioButtonChoice2)) {
            showGameMessage("The column chosen is 2\n Confirm choice by selecting yes or choose another");
            setColumn("1",radioButtonChoice2);
        }
        if (this.columnToggleGroup.getSelectedToggle().equals(radioButtonChoice3)) {
            showGameMessage("The column chosen is 3\n Confirm choice by selecting yes or choose another");
            setColumn("2",radioButtonChoice3);
        }
        if (this.columnToggleGroup.getSelectedToggle().equals(radioButtonChoice4)) {
            showGameMessage("The column chosen is 4\n Confirm choice by selecting yes or choose another");
            setColumn("3",radioButtonChoice4);
        }
        if (this.columnToggleGroup.getSelectedToggle().equals(radioButtonChoice5)) {
            showGameMessage("The column chosen is 5\n Confirm choice by selecting yes or choose another");
            setColumn("4",radioButtonChoice5);
        }
    }
    /**
     *Sets the column selected from the column toggle group
     * @see #hideYesOrNo()
     */
    private void setColumn(String s,RadioButton b) {
        yesButton.setVisible(true);
        noButton.setVisible(false);
        yesButton.setOnAction(event -> {
            viewGUI.setSelectedColumn(s);
            b.setSelected(false);
            radioButtonChoiceGrid.setVisible(false);
            chosenTilesGrid.setVisible(false);
            hideYesOrNo();
        });
    }
    /**
     *Removes all the children from the grid pane board
     */
    public void clearBoard() {
        if(!boardGrid.getChildren().isEmpty())
            boardGrid.getChildren().clear();
    }
    /**
     * Show the player the selected tiles to be ordered.
     * @param gameModelView
     * @param s
     * @see #showGameMessage(String)
     * @see #setSelectedTile(ImageView, ArrayList, ArrayList, int, Button, Label, StackPane, Boolean)
     */
    public void showSelectedTiles(GameModelView gameModelView,String s) {
        showGameMessage(s);
        chosenTilesGrid.setVisible(true);
        int size = gameModelView.getSelectedTiles().size();
        chosenTilesButtons = new ArrayList<>();
        chosenTilesLabels  = new ArrayList<>();
        boolean bool = size > 0;
        size--;
        setSelectedTile(chosenTilesButton1Image,gameModelView.getSelectedTiles(),gameModelView.getSelectedTilesId(),0,chosenTilesButton1,chosenTilesButton1Label,chosenTilesStackPane1,bool);
        bool = size > 0;
        size--;
        setSelectedTile(chosenTilesButton2Image,gameModelView.getSelectedTiles(),gameModelView.getSelectedTilesId(),1,chosenTilesButton2,chosenTilesButton2Label,chosenTilesStackPane2,bool);
        bool = size > 0;
        setSelectedTile(chosenTilesButton3Image,gameModelView.getSelectedTiles(),gameModelView.getSelectedTilesId(),2,chosenTilesButton3,chosenTilesButton3Label,chosenTilesStackPane3,bool);
    }
    /**
     * Sets the stack pane with the selected tiles information.
     * @param t
     * @param id
     * @param im
     * @param sp
     * @param b
     * @param cond
     * @param i
     * @param l
     * @see #insertImageTile(ImageView, ItemTileType, int)
     */
    private void setSelectedTile(ImageView im, ArrayList<ItemTileType> t,ArrayList<Integer> id,int i, Button b,Label l,StackPane sp, Boolean cond){
        if(cond){
            sp.setVisible(true);
            chosenTilesButtons.add(b);
            chosenTilesLabels.add(l);
            l.setText("");
            b.setDisable(true);
            insertImageTile(im,t.get(i),id.get(i));
        }else{
            sp.setVisible(false);
        }
    }
    /**
     * Asks the player to select on the button in the preferred order,adds action to yes or no buttons.
     * @param gameModelView
     * @param s
     * @see #showGameMessage(String)
     * @see #askYesOrNo()
     * @see #enableButtons()
     * @see #hideYesOrNo()
     * @see #arrangeOrder(GameModelView, int[])
     */
    public void askTileOrder(GameModelView gameModelView,String s) {
        radioButtonChoiceGrid.setVisible(false);
        showSelectedTiles(gameModelView,s);
        askYesOrNo();
        yesButton.setOnAction(event -> {
            showGameMessage("Click on the Tiles in the order you prefer:\n");
            enableButtons();
            int [] order = new int[chosenTilesButtons.size()];
            arrangeOrder(gameModelView,order);
            hideYesOrNo();
        });
        noButton.setOnAction(event -> {
            viewGUI.selectColumn(gameModelView);
        });

    }
    /**
     * Enables all the button from the chosen button list.
     */
    public void enableButtons(){
        for (Button b: chosenTilesButtons) {
            b.setDisable(false);
        }
    }
    /**
     * Show the player the selected tiles to be ordered.
     * @param gameModelView
     * @param order
     * @see #setArrangedOrder(GameModelView, int[])
     */
    public void arrangeOrder(GameModelView gameModelView,int [] order){
        chosenTilesGrid.setVisible(true);
        for (Button b: chosenTilesButtons) {
            b.setOnAction(event -> {
                int i = order.length - chosenTilesButtons.size() + 1;
                chosenTilesLabels.get(chosenTilesButtons.indexOf(b)).setText(String.valueOf(i));
                b.setEffect(setInnerGlow(50));
                order[i-1] = GridPane.getColumnIndex(b);
                chosenTilesLabels.remove(chosenTilesButtons.indexOf(b));
                chosenTilesButtons.remove(b);
                b.setDisable(true);
                setArrangedOrder(gameModelView,order);
            });
        }
    }
    /**
     * Show the player the ordered tile and set yes or no button.
     * @param order
     * @param gameModelView
     * @see #showGameMessage(String)
     * @see #disableChosenTilesButtons()
     * @see #askYesOrNo()
     * @see #askTileOrder(GameModelView, String)
     * @see #hideYesOrNo()
     */
    private void setArrangedOrder(GameModelView gameModelView,int [] order){
        if(chosenTilesButtons.isEmpty()){
            disableChosenTilesButtons();
            showGameMessage("Are you satisfied with your order?");
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
                askTileOrder(gameModelView,"In your tab are the tiles you have selected.\nDo you want to order your Tiles?");
            });
        }
    }
    /**
     * Disables the button to order the tiles.
     */
    private void disableChosenTilesButtons() {
        chosenTilesButton1.setDisable(true);
        chosenTilesButton2.setDisable(true);
        chosenTilesButton3.setDisable(true);
    }
    /**
     * Gets the order tile and returns a string of ordered tiles.
     * @param intArr
     * @return
     */
    private String getTileOrder(int [] intArr) {
        String input = "";
        int i = 0;
        while( i < intArr.length -  1) {
            input = input + intArr[i] + " ";
            i++;
        }
        input = input + intArr[i];
        return input;
    }
    /**
     * Set personal goal card button  in players tab.
     * @param gameModelView
     * @see #clearBookShelf(GridPane)
     * @see #setGlow(int, String)
     * @see #getString(ItemTileType)
     * @see #setDetailesPlayer(Tab, GridPane, Label, int, String, ItemTileType[][], int[][])
     */
    public void showPersonalGoal(GameModelView gameModelView) {
        ItemTileType[][] personalGoalCard = gameModelView.getPersonalGoalCardList().get(viewGUI.getThisUsername());
       // int [][] playerCardId = gameModelView.getPersonalGoalCardListId().get(viewGUI.getThisUsername());
        image = new Image(getClass().getResource("/view/gui/personalgoalcards/Personal_Goals"+gameModelView.getPersonalGoalCardPlayerListId().get(viewGUI.getThisUsername())+".png").toString());
        personalGoalImage.setImage(image);
        personalGoalButton.setGraphic(personalGoalImage);
        personalGoalButton.setOnAction(event -> {
            if(!clicked){
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 5; j++) {
                        if(personalGoalCard[i][j] != ItemTileType.EMPTY) {
                            Rectangle r = new Rectangle(40, 40);
                            r.setFill(Color.valueOf(getString(personalGoalCard[i][j])));
                            r.setEffect(setGlow(50, getString(personalGoalCard[i][j])));
                            personalBookshelfGrid.add(r, j, i);
                            GridPane.setMargin(r,new Insets(0,0,0,5));
                        }
                    }
                }
                clicked = true;
            }else{
                clearBookShelf(personalBookshelfGrid);
                String[] players = gameModelView.getPlayerList();
                for (int i = 0; i < players.length; i++) {
                    if(players[i].equals(viewGUI.getThisUsername())){
                        setDetailesPlayer(mainPlayerTab, personalBookshelfGrid,personalScoreLabel,gameModelView.getPointsList()[i], players[i],gameModelView.getBookshelfList()[i],gameModelView.getBookshelfListItemId()[i]);
                    }
                }
                clicked = false;
            }
        });
    }
    /**
     *Removes all the children from the bookshelf grid pane.
     */
    public void clearBookShelf(GridPane g) {
        if(!g.getChildren().isEmpty())
            g.getChildren().clear();
    }

    /**
     *Returns the inner shadow that gives the glowing effect of the color chosen.
     * @param depth
     * @param tileColor
     */
    public InnerShadow setGlow(int depth,String tileColor) {
        Color c = Color.valueOf(tileColor);
        InnerShadow innerGlow = new InnerShadow();
        innerGlow.setOffsetY(5);
        innerGlow.setOffsetX(5);
        innerGlow.setColor(c);
        innerGlow.setWidth(depth);
        innerGlow.setHeight(depth);
        return innerGlow;
    }
    /**
     *Returns the string color correspondent to the item tyle type
     * @param type
     * @return
     */
    private static String getString(ItemTileType type) {
        return switch (type) {
            case FRAME -> "MIDNIGHTBLUE";//"purple";
            case CAT -> "CHARTREUSE";//"green";
            case GAME -> "DARKORANGE";//"yellow";
            case BOOK -> "white";
            case PLANT -> "HOTPINK";//"red";
            case TROPHY -> "cyan";
            case EMPTY -> "black";
        };
    }
    /**
     * Sets to fill the common goal cards grid pane in the Common Goal Card tab
     * @param gameModelView
     * @see #initCommonGoalCard(Map.Entry, ImageView, ImageView, Label)
     */
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
    /**
     * Fill's grid pane common goal card
     * @param imageComGoalSquare
     * @param set
     * @param imageComGoalSquarePoint
     * @param labelComGoal
     *
     */
    public void initCommonGoalCard(Map.Entry<Integer, Integer[]> set, ImageView imageComGoalSquarePoint, ImageView imageComGoalSquare, Label labelComGoal) {
        String description;
        int score = set.getValue().length == 0 ? 0 : set.getValue()[0];
        image = new Image(getClass().getResource("/view/gui/scoring_tokens/scoring_"+score+".jpg").toString());
        imageComGoalSquarePoint.setImage(image);
        image = new Image(getClass().getResource("/view/gui/common_goal_cards/"+set.getKey()+".jpg").toString());
        imageComGoalSquare.setImage(image);
        description = commonGoalCardDescriptions.get(set.getKey());
        labelComGoal.setText(description);
    }

    /**
     * This method prints the messages in the scroll pane ,specifying by whom the message was sent and to whom is meant
     * @param gameModelView
     * @see #showGameMessage(String)
     * @see #returnColor(String[], String)
     */
    public void addToChat(GameModelView gameModelView) {
        String[] chat = gameModelView.getChat().toArray(new String[10]);
        int index = 0;
        for (int i = 0; i < 10; i++) {
            if (chat[i] == null) {
                break;
            }
            index = i;
        }
        String[] message = chat[index].split(":");
        showMessage("From " + message[0]+ " to All :" + message[1],returnColor(gameModelView.getPlayerList(),message[0]));
    }
    /**
     * This method prints the private messages in the scroll pane ,specifying by whom the message was sent and to whom is meant
     * @see #showMessage(String, String)
     * @see #returnColor(String[], String)
     */
    public void addToChatPrivateMessage(GameModelView gameModelView) {
        String message = gameModelView.getPrivateMessage().get(viewGUI.getThisUsername());
        if (message != null) {
            String[] splittedMessage = message.split(":");
            String[] splittedLastPrivateMessage = null;
            if (lastPrivateMessage != null) {
                splittedLastPrivateMessage = lastPrivateMessage.split(":");
            }

            if (lastPrivateMessage == null ||!splittedLastPrivateMessage[0].equals(splittedMessage[0]) || !message.equals(lastPrivateMessage)) {
                showMessage("(private) From " + splittedMessage[0] + " to " + viewGUI.getThisUsername() +": "+ splittedMessage[1],returnColor(gameModelView.getPlayerList(),splittedMessage[0]));
                lastPrivateMessage = message;
            }
        }
    }
    /**
     * This method associate each player a color for the chat
     * @param players
     * @param player

     */
    public String returnColor(String[] players, String player ) {
        String c = null;
        for (int i = 0; i < players.length;i++) {
            if(player.equals(viewGUI.getThisUsername())){
                c = "black";
            }else if(player.equals(players[i])){
                c = chatColors[i];
            }
        }
        return c;
    }
}

