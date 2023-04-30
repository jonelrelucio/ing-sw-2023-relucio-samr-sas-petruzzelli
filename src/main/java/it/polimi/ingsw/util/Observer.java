package it.polimi.ingsw.util;


import it.polimi.ingsw.distributed.events.GameEvent;

public interface Observer<SubjectType extends Observable<Event>, Event extends GameEvent> {

        void update(SubjectType o, Event arg);
}