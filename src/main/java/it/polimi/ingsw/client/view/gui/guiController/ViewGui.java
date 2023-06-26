package it.polimi.ingsw.client.view.gui.guiController;


import it.polimi.ingsw.client.view.View;
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
import java.util.concurrent.CountDownLatch;

import static it.polimi.ingsw.distributed.events.ViewEvents.EventView.*;
import static it.polimi.ingsw.distributed.events.controllerEvents.EventController.*;





public class ViewGui  extends Observable<MessageEvent> implements View, Runnable {

    private final HashMap<EventView, ViewEventHandler> viewEventHandlers;
    private ControllerUsername controllerUsername;
    private String thisUsername;
    Scene chooseUsernameScene;
    private ControllerMainSceneDalila controllerMainSceneDalila;
    Scene mainSceneDalila;
    private ControllerNplayers controllerNplayers;
    Scene selectPlayers;
    private ControllerConnection controllerConnection;
    Scene selectConnectionScene;
    private ControllerWaitingForPlayers controllerWaitingForPlayers;
    Scene waitingForPlayers;
    private final Stage window;
    private Integer change;
    private boolean isMyTurn = false;
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
        changeSceneSelectConnection();
        changeSceneChooseUsername();
        changeSceneSelectPlayers();
        changeSceneMainSceneDalila();
        changeSceneWaitingForPlayers();
        viewEventHandlers = new HashMap<>();
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
     * changes to scene in which the player chooses the username
     */
    public void changeSceneChooseUsername() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/fxml/SelectNickName.fxml"));
        try {
            Parent parentPane = loader.load();
            chooseUsernameScene = new Scene(parentPane);
            controllerUsername = loader.getController();
            controllerUsername.setViewGui(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chooseUsername() {
        Platform.runLater(() -> {
            window.setScene(chooseUsernameScene);
            window.show();
        });
    }

    /**
     * changes to scene in which the player chosees the number of players
     */

    public void changeSceneSelectPlayers() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/fxml/SelectPlayers.fxml"));
        try {
            Parent boardPaneParent = loader.load();
            selectPlayers = new Scene(boardPaneParent);
            controllerNplayers = loader.getController();
            controllerNplayers.setViewGui(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void selectNPlayers() {
        Platform.runLater(() -> {
            window.setScene(selectPlayers);
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
        return "Dalila";
    }
    @Override
    public int askMaxNumOfPlayers() {
        return 2;
    }

    private void startChat() {
        controllerMainSceneDalila.initChat();
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
        new Thread(() -> {
            printAll(gameModelView);
            listenToPlayer(gameModelView);
        }).start();
    }
    private void listenToPlayer(GameModelView gameModelView) {
        if ( isMyTurn(gameModelView)) {
            Platform.runLater(() -> {
                selectCoordinates(gameModelView,"Do you want to select a coordinate from the board?");
            });
        }else {
            Platform.runLater(()->{
                startChat();
            });
        }
    }


    @Override
    public void printMessage(String s) {
        if(Objects.equals(s, "Starting a new Game...")){
            change=2 ;
        Platform.runLater(this::showMain);
        }else if(change == 2){
            Platform.runLater(() -> {
                controllerMainSceneDalila.showMessage(s);
            });
        }else if(change == 1){
            Platform.runLater(() -> {
                controllerWaitingForPlayers.showMessage(s);
            });
        }
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

    public void printAll(GameModelView gameModelView){
        Platform.runLater(() -> {
            printCommonGoalCard(gameModelView);
            printBoard(gameModelView);
            printBookshelves(gameModelView);
            printPersonalGoal(gameModelView);
        });
    }

    /**
     * Print the end game screen
     * @param gameModel
     */
    private void printLeaderboard(GameModelView gameModel) {
        int[] pointsList = gameModel.getPointsList();
        int[] sortedList = Arrays.copyOf(pointsList, pointsList.length);
        Arrays.sort(sortedList);
        for (int i = 0; i < pointsList.length; i++) {
            int element = pointsList[i];
            int position = Arrays.binarySearch(sortedList, element);
        }
        System.out.println("Leaderboard:");
        int i = 0;
        for (Integer position : pointsList) {
            System.out.println(gameModel.getPlayerList()[position] + ": " + pointsList[position]);
        }
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

    /**
     * Print the bookshelves and the end game screen
     * @param gameModel
     * @see #printBookshelves(GameModelView)
     * @see #printLeaderboard(GameModelView)
     */
    private void endGame(GameModelView gameModel) {
        Platform.runLater(() -> {
            printLeaderboard(gameModel);
        });
    }

    /**
     * Print the last message in the chat
     * @param gameModelView
     */
    private void showChat(GameModelView gameModelView) {
        if (isMyTurn(gameModelView) && !chatAvailability) {
            return;
        }
        String[] chat = gameModelView.getChat().toArray(new String[10]);
        int index = 0;
        for (int i = 0; i < 10; i++) {
            if (chat[i] == null) {
                break;
            }
            index = i;
        }

        String[] message = chat[index].split(":");

        String color = "\033[0;33m";

        if (message[0].equals(thisUsername)) {
            color = "\033[0;34m";
        }

        System.out.println(color + message[0] + ":" + "\033[0m"  + message[1]);
    }

}


@FunctionalInterface
interface ViewEventHandler {
    void performAction(GameModelView gameModelView);
}
