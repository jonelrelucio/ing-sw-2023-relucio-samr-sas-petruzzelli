package it.polimi.ingsw.server.model.util;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.PersonalGoalCard;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.bag.PersonalGoalCardBag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CircularArrayListTest {
    static int num = 3;
    static PersonalGoalCard pgc = PersonalGoalCardBag.drawPersonalGoalCard(num);
    static Board board = new Board(num);
    static CircularArrayList<Player> listPlayer;
    static Player player1, player2, player3;

//    @BeforeAll
//    public static void init(){
//        player1 = new Player("Jonel", pgc, board);
//        player2 = new Player("Lucian", pgc, board);
//        player3 = new Player("Alessandro", pgc, board);
//        listPlayer = new CircularArrayList<>();
//        listPlayer.add(player1);
//        listPlayer.add(player2);
//        listPlayer.add(player3);
//
//    }
//
//    @Test
//    public void testGet(){
//        assertEquals(player1, listPlayer.get(0));
//        assertEquals(player2, listPlayer.get(1));
//        assertEquals(player3, listPlayer.get(2));
//        assertEquals(player1, listPlayer.get(3));
//        assertEquals(player2, listPlayer.get(4));
//        assertEquals(player3, listPlayer.get(5));
//    }
//
//    @Test
//    public void testContainsNickname(){
//        assertTrue(listPlayer.contains("Jonel"));
//        assertFalse(listPlayer.contains("Dalila"));
//        assertFalse(listPlayer.contains(""));
//        String nullName = null;
//        assertThrows(NullPointerException.class, () -> {listPlayer.contains(nullName);});
//        assertTrue(listPlayer.contains(player1));
//    }


}