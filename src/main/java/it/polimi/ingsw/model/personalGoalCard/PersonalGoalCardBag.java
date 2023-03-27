package it.polimi.ingsw.model.personalGoalCard;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class PersonalGoalCardBag {
    private static Stack<Integer> bag = new Stack<>();

    static {
        Integer[] cards = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        bag.addAll(Arrays.asList(cards));
        Collections.shuffle(bag);
    }
    public static int getRandomPersonalCardNum(){
        return bag.pop();
    }

    public static void refill() {
        Integer[] cards = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        bag.addAll(Arrays.asList(cards));
        Collections.shuffle(bag);
    }


}
