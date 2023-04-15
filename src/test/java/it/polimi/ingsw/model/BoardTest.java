package it.polimi.ingsw.model;


import it.polimi.ingsw.model.ItemTile.ItemTile;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest{
    private Board board;
    private final Bookshelf bookshelf = new Bookshelf();

    @Test
    public void test2PlayersBoard() {
        try {
            board = new Board(2);
            board.printBoard();
            System.out.println("");
        } catch (InvalidParameterException ignored) {};
    }
    @Test
    public void testCanBeSelectedTiles2() {
        board = new Board(2);
        System.out.println(board.getCanBeSelectedCoordinates());
        assertEquals(16, board.getCanBeSelectedCoordinates().size());
    }

    @Test
    public void test3PlayersBoard() {
        try {
            board = new Board(3);
            board.printBoard();
            System.out.println("");
        } catch (InvalidParameterException ignored) {};
    }
    @Test
    public void testCanBeSelectedTiles3() {
        board = new Board(3);
        System.out.println(board.getCanBeSelectedCoordinates());
        assertEquals(20, board.getCanBeSelectedCoordinates().size());
    }

    @Test
    public void test4PlayersBoard() {
        try {
            board = new Board(4);
            board.printBoard();
            System.out.println("");
        } catch (InvalidParameterException ignored) {};
    }
    @Test
    public void testCanBeSelectedTiles4() {
        board = new Board(4);
        System.out.println(board.getCanBeSelectedCoordinates());
        assertEquals(21, board.getCanBeSelectedCoordinates().size());
    }



}