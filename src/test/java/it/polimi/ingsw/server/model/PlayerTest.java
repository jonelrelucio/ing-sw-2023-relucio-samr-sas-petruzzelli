package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import it.polimi.ingsw.server.model.bag.PersonalGoalCardBag;
import it.polimi.ingsw.server.model.commonGoalCard.CommonGoalCard;
import it.polimi.ingsw.server.model.commonGoalCard.CommonGoalShape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    Player playerTest;
    PersonalGoalCard personalGoalCardTest;
    Board boardTest;


    @Test
    public void testGetEndGameToken() {
        boardTest = new Board(2);
        PersonalGoalCardBag.reset();
        personalGoalCardTest = PersonalGoalCardBag.drawPersonalGoalCard(2);
        playerTest = new Player("Test Player", personalGoalCardTest, boardTest );
        System.out.println("");
        PersonalGoalCardBag.reset();
        playerTest.setWinner();
        assertEquals(1, playerTest.getEndGameToken());
    }

    @Test
    public void testDeselectTile() {
        boardTest = new Board(2);
        PersonalGoalCardBag.reset();
        personalGoalCardTest = PersonalGoalCardBag.drawPersonalGoalCard(2);
        playerTest = new Player("Test Player", personalGoalCardTest, boardTest );
        System.out.println("");
        PersonalGoalCardBag.reset();
        playerTest.selectCoordinates(new int[]{2, 4});
        playerTest.selectCoordinates(new int[]{3, 4});
        assertEquals(2, playerTest.getBoard().getSelectedCoordinates().size());
        playerTest.deselectCoordinates(new int[]{2, 4});
        assertEquals(1, playerTest.getBoard().getSelectedCoordinates().size());
    }


    @Test
    public void testSelectedTile() {
        boardTest = new Board(2);
        PersonalGoalCardBag.reset();
        personalGoalCardTest = PersonalGoalCardBag.drawPersonalGoalCard(2);
        playerTest = new Player("Test Player", personalGoalCardTest, boardTest );
        System.out.println("");
        PersonalGoalCardBag.reset();
        playerTest.selectCoordinates(new int[]{2, 4});
        playerTest.selectCoordinates(new int[]{3, 4});
        assertEquals(2, playerTest.getBoard().getSelectedCoordinates().size());
    }

    @Test
    public void testGetSelectedItemTiles() {
        boardTest = new Board(2);
        PersonalGoalCardBag.reset();
        personalGoalCardTest = PersonalGoalCardBag.drawPersonalGoalCard(2);
        playerTest = new Player("Test Player", personalGoalCardTest, boardTest );
        System.out.println("");
        PersonalGoalCardBag.reset();
        playerTest.selectCoordinates(new int[]{2, 4});
        playerTest.selectCoordinates(new int[]{3, 4});
        playerTest.pickSelectedItemTiles();
        assertEquals(0, playerTest.getBoard().getSelectedCoordinates().size());
        assertEquals(2, playerTest.getSelectedItemTiles().size());
        System.out.println("TestCanBeSelectedItemTiles:");
        for (int i = 0; i < playerTest.getSelectedItemTiles().size(); i++){
            System.out.println( playerTest.getSelectedItemTiles().get(i).getItemTileType().toString());
            assertFalse( playerTest.getSelectedItemTiles().get(i).isEmpty());
        }
    }

    @Test
    public void TestRearrangeItems() {
        boardTest = new Board(2);
        PersonalGoalCardBag.reset();
        personalGoalCardTest = PersonalGoalCardBag.drawPersonalGoalCard(2);
        playerTest = new Player("Test Player", personalGoalCardTest, boardTest );
        System.out.println("");
        PersonalGoalCardBag.reset();
        playerTest.selectCoordinates(new int[]{2, 4});
        playerTest.selectCoordinates(new int[]{3, 4});
        playerTest.pickSelectedItemTiles();
        System.out.println("TestRearrangeItems:");
        System.out.println("    Before:");
        for (int i = 0; i < playerTest.getSelectedItemTiles().size(); i++){
            System.out.println( playerTest.getSelectedItemTiles().get(i).getItemTileType().toString());
        }
        playerTest.rearrangeSelectedItemTiles(1, 0);
        System.out.println("    After:");
        for (int i = 0; i < playerTest.getSelectedItemTiles().size(); i++){
            System.out.println( playerTest.getSelectedItemTiles().get(i).getItemTileType().toString());
        }
    }

    // Exception tests

    @Test
    public void testGetEndGameTokenWithoutWinning() {
        boardTest = new Board(2);
        PersonalGoalCardBag.reset();
        personalGoalCardTest = PersonalGoalCardBag.drawPersonalGoalCard(2);
        playerTest = new Player("Test Player", personalGoalCardTest, boardTest );
        System.out.println("");
        PersonalGoalCardBag.reset();
        try {
            playerTest.getEndGameToken();
        } catch (IllegalCallerException ignored) {System.out.println("IllegalCallerExpetion in getEndGameToken() captured.");}
    }

    @Test
    public void TestSelectCoordinatesException() {
        boardTest = new Board(2);
        PersonalGoalCardBag.reset();
        personalGoalCardTest = PersonalGoalCardBag.drawPersonalGoalCard(2);
        playerTest = new Player("Test Player", personalGoalCardTest, boardTest );
        System.out.println("");
        PersonalGoalCardBag.reset();
        try {
            playerTest.selectCoordinates(new int[]{0, 0});
        } catch (RuntimeException ignore)  {System.out.println("RuntimeException in selectCoordinates() captured.");};
    }

    @Test
    public void TestDeselectCoordinatesException() {
        boardTest = new Board(2);
        PersonalGoalCardBag.reset();
        personalGoalCardTest = PersonalGoalCardBag.drawPersonalGoalCard(2);
        playerTest = new Player("Test Player", personalGoalCardTest, boardTest );
        System.out.println("");
        PersonalGoalCardBag.reset();
        try {
            playerTest.deselectCoordinates(new int[]{0, 0});
        } catch (RuntimeException ignore)  {System.out.println("RuntimeException in deselectCoordinates() captured.");};
    }

    @Test
    public void TestRearrangeSelectedItems() {
        boardTest = new Board(2);
        PersonalGoalCardBag.reset();
        personalGoalCardTest = PersonalGoalCardBag.drawPersonalGoalCard(2);
        playerTest = new Player("Test Player", personalGoalCardTest, boardTest );
        System.out.println("");
        PersonalGoalCardBag.reset();
        playerTest.selectCoordinates(new int[]{2, 4});
        playerTest.selectCoordinates(new int[]{3, 4});
        try {
            playerTest.rearrangeSelectedItemTiles(0, 2, 1);
        } catch (RuntimeException ignore)  {System.out.println("IllegalArgumentException in rearrangeSelectedItemTiles() captured. Invalid new order");};
        try {
            playerTest.rearrangeSelectedItemTiles();
        } catch (RuntimeException ignore)  {System.out.println("IllegalArgumentException in rearrangeSelectedItemTiles() captured. Invalid new order");};
    }

    @Test
    public void TestRearrangeSelectedItems2() {
        boardTest = new Board(2);
        PersonalGoalCardBag.reset();
        personalGoalCardTest = PersonalGoalCardBag.drawPersonalGoalCard(2);
        playerTest = new Player("Test Player", personalGoalCardTest, boardTest );
        System.out.println("");
        PersonalGoalCardBag.reset();
        playerTest.selectCoordinates(new int[]{2, 4});
        playerTest.selectCoordinates(new int[]{3, 4});
        try {
            playerTest.rearrangeSelectedItemTiles(0, 10);
        } catch (RuntimeException ignore)  {System.out.println("IllegalArgumentException in rearrangeSelectedItemTiles() captured. Invalid Index");};
        try {
            playerTest.rearrangeSelectedItemTiles(-1, 10);
        } catch (RuntimeException ignore)  {System.out.println("IllegalArgumentException in rearrangeSelectedItemTiles() captured. Invalid Index");};

    }


    // Setters and getter tests

    @Test
    public void testGetters() {
        boardTest = new Board(2);
        PersonalGoalCardBag.reset();
        personalGoalCardTest = PersonalGoalCardBag.drawPersonalGoalCard(2);
        playerTest = new Player("Test Player", personalGoalCardTest, boardTest );
        System.out.println("");
        PersonalGoalCardBag.reset();
        assertEquals("Test Player", playerTest.getNickname());
        assertEquals(0, playerTest.getScore());
        assertNotNull(playerTest.getBookshelf());
        assertEquals(personalGoalCardTest, playerTest.getPersonalGoalCard());
        assertTrue(playerTest.getSelectedItemTiles().isEmpty());
        assertEquals(0, playerTest.getNumOfRounds());
        assertNull(playerTest.getPlayerState());
        assertTrue(playerTest.getObtainedCommonGoalCards().isEmpty());
        assertEquals(0, playerTest.getObtainedCommonGoalPoints());
        assertFalse(playerTest.isWinner());
    }

    @Test
    public void testSetters() {
        boardTest = new Board(2);
        PersonalGoalCardBag.reset();
        personalGoalCardTest = PersonalGoalCardBag.drawPersonalGoalCard(2);
        playerTest = new Player("Test Player", personalGoalCardTest, boardTest );
        System.out.println("");
        PersonalGoalCardBag.reset();
        playerTest.setScore(10);
        assertEquals(10, playerTest.getScore());

        Bookshelf bookshelf = new Bookshelf();
        playerTest.setBookshelf(bookshelf);
        assertEquals(bookshelf, playerTest.getBookshelf());

        PersonalGoalCard newPersonalGoalCard = PersonalGoalCardBag.drawPersonalGoalCard(3);
        playerTest.setPersonalGoalCard(newPersonalGoalCard);
        assertEquals(newPersonalGoalCard, playerTest.getPersonalGoalCard());

        ItemTile itemTile = new ItemTile(ItemTileType.BOOK);
        playerTest.getSelectedItemTiles().add(itemTile);
        assertFalse(playerTest.getSelectedItemTiles().isEmpty());

        playerTest.setNumOfRounds(1);
        assertEquals(1, playerTest.getNumOfRounds());

        playerTest.setPlayerState(PlayerState.PLAYING);
        assertEquals(PlayerState.PLAYING, playerTest.getPlayerState());

        int[][] coords = {{0,0}, {0,1}, {1,0}, {1,1}};
        CommonGoalCard commonGoalCard = new CommonGoalShape(3, 2, 2, coords, 2, false, false, false, false, true);
        playerTest.setObtainedCommonGoalCards(commonGoalCard);
        assertEquals(commonGoalCard, playerTest.getObtainedCommonGoalCards().get(0));

        playerTest.setObtainedCommonGoalPoints(5);
        assertEquals(5, playerTest.getObtainedCommonGoalPoints());

        playerTest.setWinner();
        assertTrue(playerTest.isWinner());
    }

}