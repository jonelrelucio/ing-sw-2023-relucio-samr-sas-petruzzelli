package it.polimi.ingsw.client.view;

import it.polimi.ingsw.distributed.events.GameEvent;

public interface View extends Runnable{
    void handleViewEvent( GameEvent event);

    int getNumInput();
}
