package it.polimi.ingsw.client.view.gui;


import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.gui.guiController.ViewGui;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class GUI extends Application {

    public static ViewGui viewGUI;
    private static GameModelView gameModelView;

    public static void startView() {
        launch();
    }

    public static void setViewGUI(ViewGui view, GameModelView gmv) {
        viewGUI = view;
        gameModelView = gmv;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/fxml/MainSceneDalila.fxml"));
        Pane root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("MyShelfie!");
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.setResizable(false);
        viewGUI.setWindow(primaryStage);
        viewGUI.newTurn(gameModelView);
    }

}
