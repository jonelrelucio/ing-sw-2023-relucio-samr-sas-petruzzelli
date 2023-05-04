package it.polimi.ingsw.server.model.bag;

import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import it.polimi.ingsw.server.model.PersonalGoalCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class PersonalGoalCardBagTest {

    @BeforeEach
    void setUp() {
        PersonalGoalCardBag.reset();
    }

    @Test
    public void testInitPersonalGoalCardBag() {
        int numOfPlayers = 2;
        Stack<Integer> personalGoalCardBag = new Stack<>();
        PersonalGoalCardBag.initPersonalGoalCardBag(numOfPlayers);
        assertFalse(PersonalGoalCardBag.getPersonalGoalCardBag().isEmpty());
        assertEquals(12, PersonalGoalCardBag.getPersonalGoalCardBag().size());

        // Test that the bag is initialized with the correct cards
        Integer[] expectedCards = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        assertTrue(PersonalGoalCardBag.getPersonalGoalCardBag().containsAll(Arrays.asList(expectedCards)));

        // Test that the bag is shuffled
        assertNotEquals(Arrays.asList(expectedCards),PersonalGoalCardBag.getPersonalGoalCardBag());
    }

    @Test
    public void testDrawPersonalGoalCard() {
        // Initialize the personal goal card bag
        PersonalGoalCardBag.drawPersonalGoalCard(3);
        // Draw a personal goal card
        PersonalGoalCard pgc = PersonalGoalCardBag.drawPersonalGoalCard(3);
        // Verify that the personal goal card is not null
        assertNotNull(pgc);
        // Verify that the personal goal card has the correct dimensions
        assertEquals(6, pgc.getPersonalGoalCardMatrix().length);
        assertEquals(5, pgc.getPersonalGoalCardMatrix()[0].length);
        // Verify that the personal goal card has no duplicate item tiles
        HashSet<ItemTileType> uniqueItemTiles = new HashSet<>();
        for (int i = 0; i < pgc.getPersonalGoalCardMatrix().length; i++) {
            for (int j = 0; j < pgc.getPersonalGoalCardMatrix()[0].length; j++) {
                if (!pgc.getPersonalGoalCardMatrix()[i][j].isEmpty())
                    assertTrue(uniqueItemTiles.add(pgc.getPersonalGoalCardMatrix()[i][j].getItemTileType()));
            }
        }
    }

    @Test
    public void testDrawPersonalGoalCardWithKey() {
        // Draw a specific personal goal card
        PersonalGoalCard pgc = PersonalGoalCardBag.drawPersonalGoalCard(3, 4);
        // Verify that the personal goal card is not null
        assertNotNull(pgc);
        // Verify that the personal goal card has the correct dimensions
        assertEquals(6,  pgc.getPersonalGoalCardMatrix().length);
        assertEquals(5, pgc.getPersonalGoalCardMatrix()[0].length);
        // Verify that the personal goal card has the correct item tiles
        assertEquals(ItemTileType.GAME, pgc.getPersonalGoalCardMatrix()[0][4].getItemTileType());
        assertEquals(ItemTileType.TROPHY,  pgc.getPersonalGoalCardMatrix()[2][0].getItemTileType());
        assertEquals(ItemTileType.EMPTY, pgc.getPersonalGoalCardMatrix()[0][0].getItemTileType());
    }

    @Test
    public void testDrawPersonalGoalCardWithInvalidKey() {
        // Try to draw a specific personal goal card with an invalid key
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {PersonalGoalCard pgc = PersonalGoalCardBag.drawPersonalGoalCard(3, 13);} );
    }

    @Test
    public void testNumOfDrawsPersonalGoalCard(){
        // Draw a personal goal card
        PersonalGoalCard pgc = PersonalGoalCardBag.drawPersonalGoalCard(3);
        // Verify that the number of draws has correct dimensions
        assertEquals(1, PersonalGoalCardBag.getNumOfDraws());

        // Draw another personal goal card
        pgc = PersonalGoalCardBag.drawPersonalGoalCard(3);
        // Verify that the number of draws has correct dimensions
        assertEquals(2, PersonalGoalCardBag.getNumOfDraws());

        // Draw another personal goal card
        pgc = PersonalGoalCardBag.drawPersonalGoalCard(3);
        // Verify that the number of draws has correct dimensions
        assertEquals(3, PersonalGoalCardBag.getNumOfDraws());

        //Verify that you can't draw anymore personal goal cards
        assertThrows(IllegalCallerException.class, () -> {PersonalGoalCard pgc1 = PersonalGoalCardBag.drawPersonalGoalCard(3);});
    }
}