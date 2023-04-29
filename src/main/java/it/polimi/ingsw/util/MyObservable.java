package it.polimi.ingsw.util;

import it.polimi.ingsw.events.GameEvent;

public interface MyObservable {

    void addObserver(MyObserver observer);
    void deleteObserver(MyObserver observer);
    void notifyObservers(GameEvent e);
    void deleteObservers();


}
