package it.polimi.ingsw.util;


import it.polimi.ingsw.distributed.events.GameEvent;

import java.rmi.RemoteException;

public interface Observer<SubjectType extends Observable<Event>, Event extends GameEvent> {

        void update(SubjectType o, Event arg) ;
}