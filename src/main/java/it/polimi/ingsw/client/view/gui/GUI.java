package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.distributed.events.GameEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application implements View {


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
}