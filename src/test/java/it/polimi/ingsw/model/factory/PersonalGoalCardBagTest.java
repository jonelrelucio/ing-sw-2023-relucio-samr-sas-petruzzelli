package it.polimi.ingsw.model.factory;

import it.polimi.ingsw.model.PersonalGoalCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonalGoalCardBagTest {
    @Test
    public void testBag() {
        PersonalGoalCard pg = PersonalGoalCardBag.drawPersonalGoalCard(3, 1);
        pg.printPersonalGoal();
        PersonalGoalCardBag.resetBag();
    }
}