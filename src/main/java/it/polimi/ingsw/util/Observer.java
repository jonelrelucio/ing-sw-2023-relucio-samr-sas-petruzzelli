package it.polimi.ingsw.util;

import it.polimi.ingsw.events.GameEvent;

public interface Observer<SubjectType extends Observable<Event>, Event extends GameEvent> {
    //void update(MyObservable observable, GameEvent e);
    /*
    Magari poi mettere un update per ogni tipo di evento che si può verificare
    -void update(MyObservable observable, PlayerEvent e)
    -void update(MyObservable observable, ConnectedEvent e)
    -void update(MyObservable observable, NumOfPlayerEvent e)
    -etc
     */

        void update(SubjectType o, Event arg);
}