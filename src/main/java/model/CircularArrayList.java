package model;

import java.util.ArrayList;

public class CircularArrayList<E> extends ArrayList<E> {
    @Override
    public E get(int index) {
        return super.get(index % size());
    }
}

