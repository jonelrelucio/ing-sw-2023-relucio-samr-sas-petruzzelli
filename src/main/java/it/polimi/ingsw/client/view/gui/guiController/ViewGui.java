package it.polimi.ingsw.client.view.gui.guiController;


import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.cli.Const;
import it.polimi.ingsw.distributed.events.controllerEvents.MessageEvent;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
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
import java.util.concurrent.CountDownLatch;

import static it.polimi.ingsw.distributed.events.ViewEvents.EventView.*;
import static it.polimi.ingsw.distributed.events.ViewEvents.EventView.SELECT_COLUMN_FAIL;
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
    CountDownLatch latch;
    private Integer change;


    /**
     * ViewGUI's constructor
     *
     * @param window
     */
    public ViewGui( Stage window) {
        this.window = window;
        latch = new CountDownLatch(1);
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
//    public synchronized String askUsername() {
//        chooseUsername();
//        new Thread(()->{
//            try{
//                latch.await();
//            } catch (Exception e){
//                System.out.println(e);
//            }
//        }).start();
//        return controllerUsername.getUsername();
//    }
//    @Override
//    public int askMaxNumOfPlayers() {
//        new Thread(()->{
//            try{
//                controllerUsername.showButton();
//                latch.await();
//            } catch (Exception e){
//                System.out.println(e);
//            }
//        }).start();
//        return controllerNplayers.getSelectedNumber();
//    }
    @Override
    public String askUsername() {
        return "Dalila";
    }
    @Override
    public int askMaxNumOfPlayers() {
        return 2;
    }

    public void unLock(){
        latch.countDown();
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
    public void askTileOrder(GameModelView gameModelView) {
        controllerMainSceneDalila.askTileOrder(gameModelView);
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

    public void printSelectedTiles(GameModelView gameModelView) {
        controllerMainSceneDalila.showSelectedTiles(gameModelView);
    }

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
            printSelectedTiles(gameModelView);
            if (gameModelView.getSelectedTiles().size() <= 1) selectColumn(gameModelView);
            else askTileOrder(gameModelView);
        });
    }


    private void newOrderSuccess(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        Platform.runLater(() -> {
            printSelectedTiles(gameModelView);
            askTileOrder(gameModelView);
        });
    }

    private void newOrderFail(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        Platform.runLater(() -> {
            //System.out.println("The tile order is invalid.");
            askTileOrder(gameModelView);
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
}


@FunctionalInterface
interface ViewEventHandler {
    void performAction(GameModelView gameModelView);
}
