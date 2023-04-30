package it.polimi.ingsw.util;



import it.polimi.ingsw.controller.events.GameEvent;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class Observable<Event extends GameEvent> {

    private boolean changed = false;
    private ArrayList obs;


    public Observable() {
        obs = new ArrayList ();
    }

    public synchronized void addObserver(Observer<? extends Observable<Event>, Event> o ){
        if (o == null)
            throw new NullPointerException ();
        if (!obs.contains(o)) {
            obs.add(o);
            System.out.println("Observer Added size "+obs.size());
        }
    }

    public synchronized void deleteObserver(Observer<? extends Observable<Event>, Event> o) {
        obs.remove(o);
    }

    public void notifyObservers()  {
        notifyObservers(null);
    }

    public void notifyObservers (Event arg)  {
        Object[] arrLocal;

        synchronized (this) {
            if (!changed)
                return;
            arrLocal = obs.toArray();
            System.out.println ("obs arr size"+obs.size());
            clearChanged();
        }
        System.out.println(""+arg+"  arrSize   "+ arrLocal.length );
        for  (int i = arrLocal.length-1; i>=0; i--)
            ((Observer<Observable<Event>, Event>)arrLocal[i]).update(this, arg);
    }

    public synchronized void deleteObservers() {
        obs.clear();
    }

    public synchronized void setChanged() {
        changed = true;
    }

    protected synchronized void clearChanged() {
        changed = false;
    }

    public synchronized boolean hasChanged() {
        return changed;
    }

    public synchronized int countObservers() {
        return obs.size();
    }

}