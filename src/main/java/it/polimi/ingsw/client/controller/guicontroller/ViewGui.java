package it.polimi.ingsw.client.controller.guicontroller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewGui {

    private ControllerUsername controllerUsername;

    Scene chooseUsernameScene;
    private ControllerMainScene controllerMainScene;
    Scene mainScene;
    private ControllerNplayers controllerNplayers;
    Scene selectPlayers;


    private final Stage window;

    /**
     * ViewGUI's constructor
     *
     * @param window
     */
    public ViewGui(Stage window) {
        this.window = window;
        changeSceneSelectPlayers();
        changeSceneChooseUsername();
        changeSceneMainScene();
    }

    /**
     * changes to scene in which the player chooses the username
     */


    public void changeSceneChooseUsername() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/fxml/SelectNickName.fxml"));
        try {
            Parent boardPaneParent = loader.load();
            chooseUsernameScene = new Scene(boardPaneParent);
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

    public void selectNPlayers(){
        Platform.runLater(()-> {
            window.setScene(selectPlayers);
            window.show();});
    }

    /**
     * changes to Main scene
     */


    public void changeSceneMainScene() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/fxml/MainScene.fxml"));
        try {
            Parent boardPaneParent = loader.load();
            mainScene = new Scene(boardPaneParent);
            controllerMainScene = loader.getController();
            controllerMainScene.setViewGui(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showMain(){
        Platform.runLater(()-> {
            window.setScene(mainScene);
            window.show();});
    }
}