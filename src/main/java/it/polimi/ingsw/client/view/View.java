package it.polimi.ingsw.client.view;

import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.model.GameModelView;
import it.polimi.ingsw.util.Observable;

public interface View {
    void run();
    void handleViewEvent(GameModelView gameModelView, GameEvent event);
}
