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
    private ImageView[][] cells;
    private ToggleGroup columnToggleGroup;

    private static ViewGui viewGUI;
    private static final int height = 40;
    private static final int width = 35;
    private static Boolean clicked;
    private String[] chatColors = new String[]{"red","blue","orange","green"};

    private HashMap<Integer, String> commonGoalCardDescriptions;




    public void setViewGui(ViewGui viewGUI) {
        ControllerMainSceneDalila.viewGUI = viewGUI;
    }

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
        commonGoalCardDescriptions = new HashMap<>();
        initCommonGoalCardDescription();
        initLabelPersonalTiles();
        imageComGoal1Square.setCache(false);
        imageComGoal1SquarePoint.setCache(false);
        imageComGoal2Square.setCache(false);
        imageComGoal2SquarePoint.setCache(false);
        clicked = false;
        chatTextField.clear();
    }

    private void initLabelPersonalTiles() {
        chosenTilesButton1Label.setText("");
        chosenTilesButton3Label.setText("");
        chosenTilesButton2Label.setText("");
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
    public void showMessage(String s,String c){
        textChat = new Text(s + "\n");
        //String style = "-fx-text-fill: " + c + ";";
        //textChat.setStyle(style);
        textChat.setFill(Color.valueOf(c));
        chatTextFlow.getChildren().add(textChat);
    }

    public void showGameMessage(String s){
        clearMessage();
        text = new Text(s + "\n");
        text.setFont(Font.font("Century Schoolbook", FontWeight.NORMAL,25));
        textFlowGameMessages.getChildren().add(text);
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

    public void setChatButtonSend(){
        if(!chatTextField.getText().isEmpty()) {
            viewGUI.setNewMessage(chatTextField.getText());
            chatTextField.clear();
        }
    }


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

    private void setNodeBoardTileButton (GridPane gridPane,int row, int col,ItemTileType t,int id){
        Button button = new Button();
        button.setPrefSize(height,width);
        button.setGraphic(createImageTile(t,id,false));
        button.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
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
    public void askYesOrNo() {
        yesButton.setVisible(true);
        noButton.setVisible(true);
    }

    public void hideYesOrNo() {
        yesButton.setVisible(false);
        noButton.setVisible(false);
    }

    public void printCanBeSelectedCoordinates() {
        for ( Button b : buttons){
            b.setEffect(getInnerGlow(50));
            b.setOnAction(event -> {
                int [] coor = new int[2];
                coor [0] = GridPane.getRowIndex(b)==null ? 1 : GridPane.getRowIndex(b)+1;
                coor [1] = GridPane.getColumnIndex(b) == null ? 1 : GridPane.getColumnIndex(b)+1;
                String coordinates = getCoordinates(coor);
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

    private void hideSelectedTilesPanes() {
        chosenTilesStackPane1.setVisible(false);
        chosenTilesStackPane2.setVisible(false);
        chosenTilesStackPane3.setVisible(false);
    }

    private void insertImageTile(ImageView im,ItemTileType t,int id){
        //Random random = new Random();
        //int ran = random.nextInt(4 - 1) + 1;
        image = new Image(getClass().getResource("/view/gui/item_tiles/" + t + "1."+ id +".png").toString());
        im.setImage(image);
    }

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


    public void showColumnToggle() {
        showGameMessage("select a radio button on top of the column where you want to put your tiles.\n");
        radioButtonChoiceGrid.setVisible(true);
        hideYesOrNo();
    }

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

    public void clearBoard() {
        if(!boardGrid.getChildren().isEmpty())
            boardGrid.getChildren().clear();
    }

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

    private void setSelectedTile(ImageView im, ArrayList<ItemTileType> t,ArrayList<Integer> id,int i, Button b,Label l,StackPane sp, Boolean cond){
        if(cond){
            sp.setVisible(true);
            chosenTilesButtons.add(b);
            chosenTilesLabels.add(l);
            l.setText("");
            b.setDisable(false);
            insertImageTile(im,t.get(i),id.get(i));
        }else{
            sp.setVisible(false);
        }
    }

    public void askTileOrder(GameModelView gameModelView,String s) {
        radioButtonChoiceGrid.setVisible(false);
        showSelectedTiles(gameModelView,s);
        askYesOrNo();
        yesButton.setOnAction(event -> {
            showGameMessage("Click on the Tiles in the order you prefer:\n");
            int [] order = new int[chosenTilesButtons.size()];
            arrangeOrder(gameModelView,order);
            hideYesOrNo();
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
                chosenTilesLabels.get(chosenTilesButtons.indexOf(b)).setText(String.valueOf(i));
                b.setEffect(getInnerGlow(50));
                order[i-1] = GridPane.getColumnIndex(b);
                chosenTilesLabels.remove(chosenTilesButtons.indexOf(b));
                chosenTilesButtons.remove(b);
                b.setDisable(true);
                setArrangedOrder(gameModelView,order);
            });
        }
    }

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

    private void disableChosenTilesButtons() {
        chosenTilesButton1.setDisable(true);
        chosenTilesButton2.setDisable(true);
        chosenTilesButton3.setDisable(true);
    }

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
                        Rectangle r = new Rectangle();
                        r.setFill( Color.valueOf(getString(personalGoalCard[i][j])));
                        r.setEffect(setGlow(50, getString(personalGoalCard[i][j])));
                        personalBookshelfGrid.add(r,i,j);
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

    public void clearBookShelf(GridPane g) {
        if(!g.getChildren().isEmpty())
            g.getChildren().clear();
    }


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

    private static String getString(ItemTileType type) {
        return switch (type) {
            case FRAME -> "purple";
            case CAT -> "green";
            case GAME -> "yellow";
            case BOOK -> "white";
            case PLANT -> "red";
            case TROPHY -> "cyan";
            case EMPTY -> "black";
        };
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
        int score = set.getValue().length == 0 ? 0 : set.getValue()[0];
        image = new Image(getClass().getResource("/view/gui/scoring_tokens/scoring_"+score+".jpg").toString());
        imageComGoalSquarePoint.setImage(image);
        image = new Image(getClass().getResource("/view/gui/common_goal_cards/"+set.getKey()+".jpg").toString());
        imageComGoalSquare.setImage(image);
        description = commonGoalCardDescriptions.get(set.getKey());
        labelComGoal.setText(description);
    }


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
        showMessage(message[0]+ ":" + message[1],returnColor(gameModelView.getPlayerList(),message[0]));
    }
    
    public String returnColor(String[] players, String player ) {
        String c = null;
        for (int i = 0; i < players.length;i++) {
            if(player.equals(players[i])){
                c = chatColors[i];
            }
        }
        return c;
    }
}

