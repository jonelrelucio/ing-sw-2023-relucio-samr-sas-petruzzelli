package it.polimi.ingsw.server.model.util;

import it.polimi.ingsw.server.model.Player;

import java.util.ArrayList;

/**
 * The circular array list extends the array list
 * @param <E> Generic element of the Circular Array List
 */
public class CircularArrayList<E> extends ArrayList<E> {

    /**
     * Gets the Element on a certain index position in the list
     * @param index index of the element to return
     * @return      the element
     */
    @Override
    public E get(int index) {
        return super.get(index % size());
    }

    /**
     * Checks if the circular array lisit contains an object
     * @param o element whose presence in this list is to be tested
     * @return  true if the element is in the list
     */
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

