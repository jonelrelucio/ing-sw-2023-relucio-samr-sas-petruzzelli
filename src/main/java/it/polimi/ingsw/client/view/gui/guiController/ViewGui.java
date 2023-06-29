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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


import static it.polimi.ingsw.distributed.events.ViewEvents.EventView.*;
import static it.polimi.ingsw.distributed.events.controllerEvents.EventController.*;





public class ViewGui  extends Observable<MessageEvent> implements View, Runnable {

    private final HashMap<EventView, ViewEventHandler> viewEventHandlers = new HashMap<>();;
    private String thisUsername;
    private ControllerMainSceneDalila controllerMainSceneDalila;
    Scene mainSceneDalila;
    private ControllerConnection controllerConnection;
    Scene selectConnectionScene;
    private ControllerWaitingForPlayers controllerWaitingForPlayers;
    Scene waitingForPlayers;
    private ControllerEndGame controllerEndGame;
    Scene endGameScene;
    private Stage window;
    private Integer change;
    private boolean isMyTurn = false;
    private boolean isWindowOpen = false;
    private boolean settedScenes = false;
    /**
     * Flag used to specify if the player can join the chat
     */
    private boolean chatAvailability = false;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // used for chat message input
    Thread chatThread;


    /**
     * ViewGUI's constructor
     *
     * @param window
     */
    public ViewGui( Stage window) {
        this.window = window;
        change =0 ;
        //changeSceneSelectConnection();
        changeSceneMainSceneDalila();
        //changeSceneWaitingForPlayers();
        changeSceneEndGame();
        initHandlers();
    }

    public ViewGui() {
        initHandlers();
    }

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

    public void setWindow(Stage window) {
        this.window = window;
    }

    /**
     * changes to scene in which the player chooses the connection
     */

    public void changeSceneSelectConnection() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/fxml/SelectConnection.fxml"));
        try {
            Parent boardPaneParent = loader.load();
            selectConnectionScene= new Scene(boardPaneParent);
            controllerConnection = loader.getController();
            controllerConnection.setViewGui(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void selectConnection() {
        Platform.runLater(() -> {
            window.setScene(selectConnectionScene);
            window.show();
        });
    }

    /**
     * changes to Main scene
     */
    private void changeSceneMainSceneDalila() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/fxml/MainSceneDalila.fxml"));
        try {
            Parent boardPaneParent = loader.load();
            mainSceneDalila = new Scene(boardPaneParent);
            controllerMainSceneDalila = loader.getController();
            controllerMainSceneDalila.setViewGui(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showMain() {
        Platform.runLater(() -> {
            window.setScene(mainSceneDalila);
            window.show();
            window.setResizable(true);
            change=2;
        });
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
    public void showEndGame() {
        Platform.runLater(() -> {
            window.setScene(endGameScene);
            window.show();
        });
    }

    private void changeSceneWaitingForPlayers() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/fxml/WaitingForOtherPlayers.fxml"));
        try {
            Parent boardPaneParent = loader.load();
            waitingForPlayers = new Scene(boardPaneParent);
            controllerWaitingForPlayers = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showWaitingPlayer() {
        Platform.runLater(() -> {
            window.setScene(waitingForPlayers);
            window.show();
            change = 1;
        });
    }
    /**
     * Registro nome palyer e numero giocatori ancora da sistemare
     *
     *
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

    private void updateChat(GameModelView gameModelView) {
        controllerMainSceneDalila.addToChat(gameModelView);
    }

    private void updatePrivateChat(GameModelView gameModelView) {
        controllerMainSceneDalila.addToChatPrivateMessage(gameModelView);
    }

    /**
     * From here event handlers
     * */
    @Override
    public void ViewEventHandler(GameModelView gameModelView, EventView eventView) {
        viewEventHandlers.get(eventView).performAction(gameModelView);
    }

    /***
     *Nuovo turno stampo la board e resto in ascolto del player
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
                changeSceneMainSceneDalila();
                changeSceneEndGame();
                window.setScene(mainSceneDalila);
                window.show();
            }
            new Thread(() -> {
                printAll(gameModelView);
                listenToPlayer(gameModelView);
            }).start();
        }
    }

    private void listenToPlayer(GameModelView gameModelView) {
        if ( isMyTurn(gameModelView)) {
            Platform.runLater(() -> {
                controllerMainSceneDalila.initChat(gameModelView.getPlayerList());
                selectCoordinates(gameModelView,"Do you want to select a coordinate from the board?");
            });
        }else {
            Platform.runLater(()->{
                controllerMainSceneDalila.showGameMessage("It's " + gameModelView.getCurrentPlayer() + "'s turn.");
                controllerMainSceneDalila.initChat(gameModelView.getPlayerList());
            });
        }
    }


    @Override
    public void printMessage(String s) {
        System.out.println(s);
    }
    @Override
    public void setThisUsername(String thisUsername) {
        this.thisUsername = thisUsername;
    }
    public String getThisUsername() {
        return thisUsername;
    }
    public void selectCoordinates(GameModelView gameModelView,String s) {
        controllerMainSceneDalila.selectCoordinates(gameModelView,s);
    }
    public void askDeselectCoordinates(GameModelView gameModelView) {
        controllerMainSceneDalila.deselectCoordinates(gameModelView);
    }
    public void pickTiles() {
        setChangedAndNotifyObservers(new MessageEvent(PICK_TILES, " "));
    }
    public void askTileOrder(GameModelView gameModelView,String s) {
        controllerMainSceneDalila.askTileOrder(gameModelView,s);
    }
    public void selectColumn(GameModelView gameModelView) {
        printBookshelves(gameModelView);
        controllerMainSceneDalila.showColumnToggle();
    }
    private void setChangedAndNotifyObservers(MessageEvent arg) {
        new Thread(() -> {
            setChanged();
            notifyObservers(arg);
        }).start();
    }
    public boolean isMyTurn(GameModelView gameModelView){
        return gameModelView.getCurrentPlayer().equals(thisUsername);
    }

    /***************************************************************************************************************/


    public void printBoard(GameModelView gameModelView) {
        controllerMainSceneDalila.showBoard(gameModelView);
    }

    public void printBookshelves(GameModelView gameModelView) {
        controllerMainSceneDalila.showBookshelves(gameModelView);
    }

    public void printPersonalGoal(GameModelView gameModelView){
        controllerMainSceneDalila.showPersonalGoal(gameModelView);
    }

    private void printCommonGoalCard(GameModelView gameModelView) {
        controllerMainSceneDalila.showCommonGoalCard(gameModelView);
    }

    /**
     * Print the end game screen
     * @param gameModel
     */
    private void printLeaderboard(GameModelView gameModel) {
        controllerEndGame.init(gameModel);
        controllerEndGame.setLeaderBookShelf(gameModel);
    }

    public void printAll(GameModelView gameModelView){
        Platform.runLater(() -> {
            printCommonGoalCard(gameModelView);
            printBoard(gameModelView);
            printBookshelves(gameModelView);
            printPersonalGoal(gameModelView);
        });
    }

    /******************************************************************************************/

    private void selectedCoordinatesFail(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        Platform.runLater(() -> {
            printAll(gameModelView);
            selectCoordinates(gameModelView,"The selected coordinates are not available\n Please choose another one:");
        });
    }

    private void selectedCoordinatesSuccess(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        Platform.runLater(() -> {
            printAll(gameModelView);
            askDeselectCoordinates(gameModelView);
        });
    }

    private void deselectCoordinatesSuccess(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        Platform.runLater(() -> {
            printAll(gameModelView);
            selectCoordinates(gameModelView, "Do you want to select coordinates?");
        });
    }

    private void deselectCoordinatesFail( GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        Platform.runLater(() -> {
            printAll(gameModelView);
            askDeselectCoordinates(gameModelView);
        });
    }

    private void pickTilesEvent(GameModelView gameModelView ) {
        if( !isMyTurn(gameModelView)) return;
        Platform.runLater(() -> {
            printAll(gameModelView);
            if (gameModelView.getSelectedTiles().size() <= 1) selectColumn(gameModelView);
            else askTileOrder(gameModelView,"In your tab are the tiles you have selected.\nDo you want to order your Tiles?");
        });
    }


    private void newOrderSuccess(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        Platform.runLater(() -> {
            askTileOrder(gameModelView,"In your tab are the tiles you have selected.\nDo you want to order your Tiles?");
        });
    }

    private void newOrderFail(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        Platform.runLater(() -> {
            askTileOrder(gameModelView,"The tile order is invalid.\nIn your tab are the tiles you have selected.\nDo you want to order your Tiles?");
        });
    }

    private void selectColumnFail(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        Platform.runLater(() -> {
            selectColumn(gameModelView);
        });
    }
    @Override
    public void run() {
    }

    public void setCoordinates(String coordinates) {
        setChangedAndNotifyObservers(new MessageEvent(SELECT_COORDINATES, coordinates));
    }
    public void setDeselectedCoordinates(String coordinates) {
        setChangedAndNotifyObservers(new MessageEvent(DESELECT_COORDINATES, coordinates));
    }
    public void setSelectedColumn(String input) {
        setChangedAndNotifyObservers(new MessageEvent(SELECT_COLUMN, input));
    }
    public void setNewOrder(String input) {
        setChangedAndNotifyObservers(new MessageEvent(NEW_ORDER, input));
    }

    public void setNewMessage(String message){
        setChangedAndNotifyObservers(new MessageEvent(NEW_MESSAGE_CHAT, thisUsername + ": " + message));
    }
    public void setNewPrivateMessage(String receiver,String message){
        setChangedAndNotifyObservers(new MessageEvent(NEW_MESSAGE_TO, receiver + " " + thisUsername + ": " + message));
    }
    /**
     * Print the bookshelves and the end game screen
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

}


@FunctionalInterface
interface ViewEventHandler {
    void performAction(GameModelView gameModelView);
}
