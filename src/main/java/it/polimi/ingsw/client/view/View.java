package it.polimi.ingsw.client.view;


public interface View extends Runnable{

    String askUsername();

    int askMaxNumOfPlayers();

    void printMessage(String string);
}
