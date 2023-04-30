package it.polimi.ingsw.util;

import it.polimi.ingsw.events.GameEvent;
import it.polimi.ingsw.events.NumOfPlayersEvent;
import it.polimi.ingsw.events.PlayerNameEvent;

public interface MyObservable {

    void addObserver(MyObserver observer);
    void deleteObserver(MyObserver observer);
    void notifyObservers(PlayerNameEvent e);
    void notifyObservers(NumOfPlayersEvent e);
    void deleteObservers();


}
