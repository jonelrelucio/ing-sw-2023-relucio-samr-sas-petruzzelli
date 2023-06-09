package it.polimi.ingsw.util;


public interface Observer<SubjectType extends Observable<Event>, Event> {

        void update(SubjectType o, Event arg) ;
}