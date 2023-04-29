package it.polimi.ingsw.util;



import it.polimi.ingsw.model.events.GameEvent;

import java.util.Vector;

public class Observable<Event extends GameEvent> {

    private boolean changed = false;
    private Vector<Observer<? extends Observable<Event>, Event>> obs;

    public Observable() {
        obs = new Vector<>();
    }

    public synchronized void addObserver(Observer<? extends Observable<Event>, Event> o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.addElement(o);
        }
    }


    public synchronized void deleteObserver(Observer<? extends Observable<Event>, Event> o) {
        obs.removeElement(o);
    }

    public void notifyObservers(Event arg) {
        Object[] arrLocal;
        synchronized (this) {
            if (!changed)
                return;
            arrLocal = obs.toArray();
            clearChanged();
        }

        for (int i = arrLocal.length-1; i>=0; i--)
            ((Observer<Observable<Event>, Event>)arrLocal[i]).update(this, arg);
    }

    public synchronized void deleteObservers() {
        obs.removeAllElements();
    }

    protected synchronized void setChanged() {
        changed = true;
    }

    protected synchronized void clearChanged() {
        changed = false;
    }


}