package it.polimi.ingsw.client.view;


import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

public interface View extends Runnable{

    void ViewEventHandler(GameModelView gameModelView, EventView eventView);

    String askUsername();

    int askMaxNumOfPlayers();

    void printMessage(String string);

    void setThisUsername(String thisUsername);

    void newTurn(GameModelView gameModelView);
}
