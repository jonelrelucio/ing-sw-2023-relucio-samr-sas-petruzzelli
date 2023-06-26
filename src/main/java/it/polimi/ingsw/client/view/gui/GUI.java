package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

public class GUI extends Application implements View {

    public static void startView() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/fxml/MyShelfieHome.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("MyShelfie!");
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void run() {
        // TODO
        System.out.println("");
    }


    @Override
    public void ViewEventHandler(GameModelView gameModelView, EventView eventView) {

    }

    @Override
    public String askUsername() {
        return null;
    }


    @Override
    public int askMaxNumOfPlayers() {
        return 0;
    }

    @Override
    public void printMessage(String string) {

    }

    @Override
    public void setThisUsername(String thisUsername) {

    }

    @Override
    public void newTurn(GameModelView gameModelView) {

    }

    public void printChat(ArrayBlockingQueue<String> chat) {

    }

}
