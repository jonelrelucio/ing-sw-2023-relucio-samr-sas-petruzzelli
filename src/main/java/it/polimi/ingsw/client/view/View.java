package it.polimi.ingsw.client.view;


import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;

import java.util.concurrent.CountDownLatch;

public interface View extends Runnable{

    String askUsername();

    int askMaxNumOfPlayers();

    void printMessage(String string);

    void update(GameModelView gameModelView);

    void setThisUsername(String thisUsername);
}
