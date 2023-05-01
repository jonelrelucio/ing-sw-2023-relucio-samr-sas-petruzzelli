package it.polimi.ingsw.client.view;

import it.polimi.ingsw.distributed.events.GameEvent;

public interface View {
    void newGame();
    void joinGame();
    void handleViewEvent( GameEvent event);
}
