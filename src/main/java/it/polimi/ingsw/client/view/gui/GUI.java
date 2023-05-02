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

    //TODO
    @Override
    public void newGame() { launch(); }

    @Override
    public void joinGame() { launch(); }

    @Override
    public void isConnected(boolean isConnected){
        System.out.println(" ");
    }

    @Override
    public void handleViewEvent(GameEvent event) {
        //TODO
        System.out.println("DO SOMETHING");
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


}
