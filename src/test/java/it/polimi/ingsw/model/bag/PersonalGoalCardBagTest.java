package it.polimi.ingsw.model.bag;

import it.polimi.ingsw.model.PersonalGoalCard;
import org.junit.jupiter.api.Test;

public class PersonalGoalCardBagTest {
    @Test
    public void testBag() {
        PersonalGoalCard pg = PersonalGoalCardBag.drawPersonalGoalCard(3, 1);
        pg.printPersonalGoal();
        PersonalGoalCardBag.resetBag();
    }
}