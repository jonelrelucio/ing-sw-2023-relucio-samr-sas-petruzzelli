package it.polimi.ingsw.client.view.gui.guiController;


import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.distributed.events.controllerEvents.MessageEvent;
import it.polimi.ingsw.util.Observable;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import java.io.IOException;
import java.util.*;


import static it.polimi.ingsw.distributed.events.ViewEvents.EventView.*;
import static it.polimi.ingsw.distributed.events.controllerEvents.EventController.*;


/**
 * This class manage all the input and all the different stages of the game using a Graphical User Interface (GUI).
 */


public class ViewGui  extends Observable<MessageEvent> implements View, Runnable {
    /**
     * HashMap that contain events as keys and #VireEventHandler as value, its purpose is to handle all the events triggered by the server.
     */
    private final HashMap<EventView, ViewEventHandler> viewEventHandlers = new HashMap<>();
    /**
     * Player's username
     */
    private String thisUsername;

    private ControllerMainScene controllerMainScene;
    Scene mainScene;
    private ControllerEndGame controllerEndGame;
    Scene endGameScene;
    private Stage window;
    private boolean isWindowOpen = false;
    private boolean settedScenes = false;




    /**
     * Initialize the viewGui and call:
     * @see #initHandlers()
     */
    public ViewGui() {
        initHandlers();
    }
    /**
     * Put all the expected events called by the server as keys of 'viewEventHandlers' HashMap and the methods used to manage the events as values
     */

    private void initHandlers() {
        viewEventHandlers.put(SELECT_COORDINATES_SUCCESS, this::selectedCoordinatesSuccess);
        viewEventHandlers.put(SELECT_COORDINATES_FAIL,  this::selectedCoordinatesFail);
        viewEventHandlers.put(DESELECT_COORDINATES_SUCCESS, this::deselectCoordinatesSuccess);
        viewEventHandlers.put(DESELECT_COORDINATES_FAIL, this::deselectCoordinatesFail);
        viewEventHandlers.put(PICK_TILES_SUCCESS, this::pickTilesEvent);
        viewEventHandlers.put(NEW_ORDER_SUCCESS, this::newOrderSuccess);
        viewEventHandlers.put(NEW_ORDER_FAIL, this::newOrderFail);
        viewEventHandlers.put(SELECT_COLUMN_FAIL, this::selectColumnFail);
        viewEventHandlers.put(NEW_TURN, this::newTurn);
        viewEventHandlers.put(END_GAME, this::endGame);
        viewEventHandlers.put(UPDATE_CHAT, this::showChat);
        viewEventHandlers.put(UPDATE_PRIVATE_CHAT, this::printPrivateMessage);
    }

    /**
     * Set the field 'window'
     * @param window The window that has to be shown
     */
    public void setWindow(Stage window) {
        this.window = window;
    }

    /**
     * changes to Main scene
     */
    private void changeSceneMainScene() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/fxml/MainSceneDalila.fxml"));
        try {
            Parent boardPaneParent = loader.load();
            mainScene = new Scene(boardPaneParent);
            controllerMainScene = loader.getController();
            controllerMainScene.setViewGui(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * changes to End Game
     */
    private void changeSceneEndGame() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/fxml/EndGameScene.fxml"));
        try {
            Parent boardPaneParent = loader.load();
            endGameScene = new Scene(boardPaneParent);
            controllerEndGame = loader.getController();
            controllerEndGame.setViewGui(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Changes the scene to endGameScene in the window.
     */
    public void showEndGame() {
        Platform.runLater(() -> {
            window.setScene(endGameScene);
            window.show();
        });
    }

    /**
     * Read the user input and check its validity.
     * @return the username chosen by the player as a String
     */
    @Override
    public String askUsername() {
        String username;
        System.out.print("Please choose your username: ");
        do {
            Scanner s = new Scanner(System.in);
            username = s.nextLine();
            if (username.length() < 3 || username.isBlank()) System.out.println("Invalid username, try again...");
        }   while( username.length() < 3 || username.isBlank() );
        return username;
    }
    /**
     * Read the user input and check its validity.
     * @return the number inserted by the user as int or recall getNumInput() if was not inserted a number
     */
    public int getNumInput(){
        try {
            Scanner s = new Scanner(System.in) ;
            return Integer.parseInt(s.nextLine());
        }
        catch (NumberFormatException ex) {
            System.out.println("Invalid input. Enter a number.");
            return getNumInput();
        }
    }
    /**
     * Read the user input and check its validity.
     * @return the max number of player for the current game
     * @see #getNumInput()
     */
    @Override
    public int askMaxNumOfPlayers() {
        int maxNumOfPlayers;
        System.out.println("Please choose maximum number of players (from 2 to 4 players can join):");
        do {
            Scanner s = new Scanner(System.in) ;
            maxNumOfPlayers = getNumInput();
            if (maxNumOfPlayers < 2 || maxNumOfPlayers > 4) System.out.println("Only from 2 to 4 players can join. Selected a number again:");
        }   while( maxNumOfPlayers < 2 || maxNumOfPlayers > 4 );
        return maxNumOfPlayers;
    }
    /**
     * Print the String parameter.
     * @param s Message to be printed
     */
    @Override
    public void printMessage(String s) {
        System.out.println(s);
    }
    /**
     * Set the field 'thisUsername'
     * @param thisUsername The username chosen by the player
     */
    @Override
    public void setThisUsername(String thisUsername) {
        this.thisUsername = thisUsername;
    }

    /**
     * Get the field 'thisUsername'
     * @return thisUsername The username chosen by the player
     */
    public String getThisUsername() {
        return thisUsername;
    }

    /**
     * From here event handlers
     * */
    @Override
    public void ViewEventHandler(GameModelView gameModelView, EventView eventView) {
        viewEventHandlers.get(eventView).performAction(gameModelView);
    }

    //TODO
    /**
     * Create a new thread that manage the game turn
     * @param gameModelView
     * @see #printAll(GameModelView)
     * @see #listenToPlayer(GameModelView)
     */
    public void newTurn(GameModelView gameModelView){
        if (!isWindowOpen) {
            isWindowOpen = true;
            GUI.setViewGUI(this, gameModelView);
            GUI.startView();
        }
        else {
            if(!settedScenes) {
                settedScenes = true;
                changeSceneMainScene();
                changeSceneEndGame();
                window.setScene(mainScene);
                window.show();
            }
            new Thread(() -> {
                printAll(gameModelView);
                listenToPlayer(gameModelView);
            }).start();
        }
    }
    /**
     * If it's the player's turn starts the sequence of action that the player must perform and initialize the chat, otherwise initialize the chat
     * @param gameModelView
     * @see #selectCoordinates(GameModelView,String)
     */
    private void listenToPlayer(GameModelView gameModelView) {
        if ( isMyTurn(gameModelView)) {
            Platform.runLater(() -> {
                controllerMainScene.initChat(gameModelView.getPlayerList());
                selectCoordinates(gameModelView,"Do you want to select a coordinate from the board?");
            });
        }else {
            Platform.runLater(()->{
                controllerMainScene.showGameMessage("It's " + gameModelView.getCurrentPlayer() + "'s turn.");
                controllerMainScene.initChat(gameModelView.getPlayerList());
            });
        }
    }
    /**
     * Calls the controller main scene method that will add to the chat the new texts.
     *
     * @param gameModelView
     */
    private void updateChat(GameModelView gameModelView) {
        controllerMainScene.addToChat(gameModelView);
    }

    /**
     * Calls the controller main scene method that will add to the chat the new private texts.
     *
     * @param gameModelView
     */
    private void updatePrivateChat(GameModelView gameModelView) {
        controllerMainScene.addToChatPrivateMessage(gameModelView);
    }
    /**
     * Calls the controller main scene method that will ask the player to choose if he wants to select a coordinate.
     *
     * @param gameModelView
     * @param s
     */
    public void selectCoordinates(GameModelView gameModelView,String s) {
        controllerMainScene.selectCoordinates(gameModelView,s);
    }

    /**
     * Calls the controller main scene method that will ask the player to choose if he wants to deselect a coordinate or not.
     *
     * @param gameModelView
     */
    public void askDeselectCoordinates(GameModelView gameModelView) {
        controllerMainScene.deselectCoordinates(gameModelView);
    }

    /**
     * Calls the controller main scene method that will ask the player if he wants to order the selected tiles.
     *
     * @param gameModelView
     */
    public void askTileOrder(GameModelView gameModelView,String s) {
        controllerMainScene.askTileOrder(gameModelView,s);
    }

    /**
     * Calls the controller main scene method that will ask the player if he wants to select the column.
     *
     * @param gameModelView
     * @see #printBookshelves(GameModelView)
     */
    public void selectColumn(GameModelView gameModelView) {
        printBookshelves(gameModelView);
        controllerMainScene.showColumnToggle();
    }
    /**
     * call setChanged() and notify all the observers with the event contained into the 'arg' parameter
     * @param arg
     * @see #setChanged()
     * @see #notifyObservers(MessageEvent)
     */
    private void setChangedAndNotifyObservers(MessageEvent arg) {
        new Thread(() -> {
            setChanged();
            notifyObservers(arg);
        }).start();
    }
    /**
     * Check if it's the player's turn
     * @param gameModelView
     * @return true if it is, otherwise false
     */
    public boolean isMyTurn(GameModelView gameModelView){
        return gameModelView.getCurrentPlayer().equals(thisUsername);
    }

    /***************************************************************************************************************/

    /**
     * Calls the controller main scene method that will show the board game
     * @param gameModelView
     */
    public void printBoard(GameModelView gameModelView) {
        controllerMainScene.showBoard(gameModelView);
    }
    /**
     * Calls the controller main scene method that will show the bookshelves of all the players
     * @param gameModelView
     */
    public void printBookshelves(GameModelView gameModelView) {
        controllerMainScene.showBookshelves(gameModelView);
    }
    /**
     * Calls the controller main scene method that will show the personal goal card
     * @param gameModelView
     */
    public void printPersonalGoal(GameModelView gameModelView){
        controllerMainScene.showPersonalGoal(gameModelView);
    }

    /**
     * Calls the controller main scene method that will show the common goal cards and their description
     * @param gameModelView
     */
    private void printCommonGoalCard(GameModelView gameModelView) {
        controllerMainScene.showCommonGoalCard(gameModelView);
    }

    /**
     * Calls the controller end game method that will show table with the name of the players and their points from the highest score to the lowest and the winning bookshelf
     * @param gameModel
     */
    private void printLeaderboard(GameModelView gameModel) {
        controllerEndGame.init(gameModel);
        controllerEndGame.setLeaderBookShelf(gameModel);
    }
    /**
     * Print all the element of the game: deck of two common goal card, board, bookshelves and personal goal card
     * @param gameModelView
     * @see #printCommonGoalCard(GameModelView)
     * @see #printBoard(GameModelView)
     * @see #printBookshelves(GameModelView)
     * @see #printPersonalGoal(GameModelView)
     */
    public void printAll(GameModelView gameModelView){
        Platform.runLater(() -> {
            printCommonGoalCard(gameModelView);
            printBoard(gameModelView);
            printBookshelves(gameModelView);
            printPersonalGoal(gameModelView);
        });
    }

    /******************************************************************************************/
    /**
     * The method is called when an error occurs during the coordinates selection step.
     * If it's the player's turn ask again to select the coordinates.
     * @param gameModelView
     * @see #isMyTurn(GameModelView)
     * @see #printAll(GameModelView)
     * @see #selectCoordinates(GameModelView,String)
     */
    private void selectedCoordinatesFail(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        Platform.runLater(() -> {
            printAll(gameModelView);
            selectCoordinates(gameModelView,"The selected coordinates are not available\n Please choose another one:");
        });
    }
    /**
     * If the coordinates selection action goes well ask if the player wants to deselect some coordinates
     * @param gameModelView
     * @see #isMyTurn(GameModelView)
     * @see #printAll(GameModelView)
     * @see #askDeselectCoordinates(GameModelView)
     */
    private void selectedCoordinatesSuccess(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        Platform.runLater(() -> {
            printAll(gameModelView);
            askDeselectCoordinates(gameModelView);
        });
    }
    /**
     * If the coordinates deselection action goes well ask if the player wants to select some coordinates
     * @param gameModelView
     * @see #isMyTurn(GameModelView)
     * @see #printAll(GameModelView)
     * @see #selectCoordinates(GameModelView,String)
     */
    private void deselectCoordinatesSuccess(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        Platform.runLater(() -> {
            printAll(gameModelView);
            selectCoordinates(gameModelView, "Do you want to select coordinates?");
        });
    }
    /**
     * The method is called when an error occurs during the coordinates deselection step.
     * If it's the player's turn ask again to deselect the coordinates.
     * @param gameModelView
     * @see #isMyTurn(GameModelView)
     * @see #printAll(GameModelView)
     * @see #askDeselectCoordinates(GameModelView)
     */
    private void deselectCoordinatesFail( GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        Platform.runLater(() -> {
            printAll(gameModelView);
            askDeselectCoordinates(gameModelView);
        });
    }
    /**
     * The method is called if the action of pick the tiles from the board goes well
     * @param gameModelView
     * @see #isMyTurn(GameModelView)
     * @see #printAll(GameModelView)
     * @see #selectColumn(GameModelView)
     * @see #askTileOrder(GameModelView,String)
     */
    private void pickTilesEvent(GameModelView gameModelView ) {
        if( !isMyTurn(gameModelView)) return;
        Platform.runLater(() -> {
            printAll(gameModelView);
            if (gameModelView.getSelectedTiles().size() <= 1) selectColumn(gameModelView);
            else askTileOrder(gameModelView,"In your tab are the tiles you have selected.\nDo you want to order your Tiles?");
        });
    }

    /**
     * If the action of set the order of the selected tiles goes well ask again if the player wants to set a new order
     * @param gameModelView
     * @see #isMyTurn(GameModelView)
     * @see #askTileOrder(GameModelView,String)
     */
    private void newOrderSuccess(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        Platform.runLater(() -> {
            askTileOrder(gameModelView,"In your tab are the tiles you have selected.\nDo you want to order your Tiles?");
        });
    }
    /**
     * If the action of set the order of the selected tiles goes wrong ask again if the player wants to set a new order
     * @param gameModelView
     * @see #isMyTurn(GameModelView)
     * @see #askTileOrder(GameModelView,String)
     */
    private void newOrderFail(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        Platform.runLater(() -> {
            askTileOrder(gameModelView,"The tile order is invalid.\nIn your tab are the tiles you have selected.\nDo you want to order your Tiles?");
        });
    }
    /**
     * If the action of select a column goes wrong ask again to select a column
     * @param gameModelView
     * @see #isMyTurn(GameModelView)
     * @see #selectColumn(GameModelView)
     */
    private void selectColumnFail(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        Platform.runLater(() -> {
            selectColumn(gameModelView);
        });
    }

    /**
     * Print the winner bookshelves and the end game table
     * @param gameModel
     * @see #printBookshelves(GameModelView)
     * @see #printLeaderboard(GameModelView)
     */
    private void endGame(GameModelView gameModel) {
        Platform.runLater(() -> {
            showEndGame();
            printLeaderboard(gameModel);
        });
    }

    /**
     * Print the last message in the chat
     * @param gameModelView
     */
    private void showChat(GameModelView gameModelView) {
        Platform.runLater(() -> {
            updateChat(gameModelView);
        });
    }

    /**
     * This method prints the last private message
     * @param gameModelView
     */
    public void printPrivateMessage(GameModelView gameModelView) {
        Platform.runLater(() -> {
            updatePrivateChat(gameModelView);
        });
    }
    @Override
    public void run() {
    }
    /**
     * Notify the server of the selection of coordinates
     * @param coordinates
     * @see #setChangedAndNotifyObservers(MessageEvent)
     */
    public void setCoordinates(String coordinates) {
        setChangedAndNotifyObservers(new MessageEvent(SELECT_COORDINATES, coordinates));
    }
    /**
     * Notify the server of the deselection of coordinates
     * @param coordinates
     * @see #setChangedAndNotifyObservers(MessageEvent)
     */
    public void setDeselectedCoordinates(String coordinates) {
        setChangedAndNotifyObservers(new MessageEvent(DESELECT_COORDINATES, coordinates));
    }
    /**
     * Notify the server to ask which item tiles pick from the board
     * @see #setChangedAndNotifyObservers(MessageEvent)
     */
    public void pickTiles() {
        setChangedAndNotifyObservers(new MessageEvent(PICK_TILES, " "));
    }
    /**
     * Notify the server of the selection of column
     * @param input
     * @see #setChangedAndNotifyObservers(MessageEvent)
     */
    public void setSelectedColumn(String input) {
        setChangedAndNotifyObservers(new MessageEvent(SELECT_COLUMN, input));
    }
    /**
     * Notify the server of the new order for the item tiles chosen
     * @param input
     * @see #setChangedAndNotifyObservers(MessageEvent)
     */
    public void setNewOrder(String input) {
        setChangedAndNotifyObservers(new MessageEvent(NEW_ORDER, input));
    }

    /**
     * Notify the server of the new message sent in chat
     * @param message
     * @see #setChangedAndNotifyObservers(MessageEvent)
     */
    public void setNewMessage(String message){
        setChangedAndNotifyObservers(new MessageEvent(NEW_MESSAGE_CHAT, thisUsername + ": " + message));
    }
    /**
     * Notify the server of the new private message sent in chat
     * @param receiver the receiver of the private message
     * @param message
     * @see #setChangedAndNotifyObservers(MessageEvent)
     */
    public void setNewPrivateMessage(String receiver,String message){
        setChangedAndNotifyObservers(new MessageEvent(NEW_MESSAGE_TO, receiver + " " + thisUsername + ": " + message));
    }

}


/**
 * Declares the 'performAction()' method
 */
@FunctionalInterface
interface ViewEventHandler {
    /**
     * Declaration of the method 'performAction()'
     * @param gameModelView
     */
    void performAction(GameModelView gameModelView);
}
