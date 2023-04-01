package it.polimi.ingsw.model.bag;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class PersonalGoalCardBag {
    private final Stack<Integer> personalGoalCardBag = new Stack<>();

    public PersonalGoalCardBag() {
        initPersonalGoalCardBag();
    }
    private  void initPersonalGoalCardBag(){
        Integer[] cards = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        personalGoalCardBag.addAll(Arrays.asList(cards));
        Collections.shuffle(personalGoalCardBag);
    }
    public int drawPersonalCardNum(){ return personalGoalCardBag.pop(); }

}
