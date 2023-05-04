package it.polimi.ingsw.client.view;


public interface View extends Runnable{

    String askUsername();

    void printUsernameNotAvailable();

    int askMaxNumOfPlayers();

    void printMessageFromServer(String string);
}
