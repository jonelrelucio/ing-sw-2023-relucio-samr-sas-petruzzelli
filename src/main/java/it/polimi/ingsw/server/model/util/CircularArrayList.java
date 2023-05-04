package it.polimi.ingsw.server.model.util;

import it.polimi.ingsw.server.model.Player;

import java.util.ArrayList;

public class CircularArrayList<E> extends ArrayList<E> {
    @Override
    public E get(int index) {
        return super.get(index % size());
    }

    @Override
    public boolean contains(Object o){
        if (o == null ) throw new NullPointerException("Nickname string is null");
        if (o instanceof String string) {
            for (E element : this) {
                if (element instanceof Player player)
                    if (string.equals(player.getNickname())) {
                        return true;
                    }
            }
            return false;
        } else return super.contains(o);
    }

}

