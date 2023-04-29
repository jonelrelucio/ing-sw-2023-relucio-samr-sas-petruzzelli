package it.polimi.ingsw.util;

import it.polimi.ingsw.events.GameEvent;
import it.polimi.ingsw.events.PlayerNameEvent;

import java.util.HashMap;

public interface MyObserver {
     //void update(MyObservable observable, GameEvent e);
    /*
    Magari poi mettere un update per ogni tipo di evento che si può verificare
    -void update(MyObservable observable, PlayerEvent e)
    -void update(MyObservable observable, ConnectedEvent e)
    -void update(MyObservable observable, NumOfPlayerEvent e)
    -etc
     */

    <GameEventType extends GameEvent> void  update(MyObservable observable, GameEventType e);
}
