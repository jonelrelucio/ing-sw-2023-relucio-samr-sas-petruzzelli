package it.polimi.ingsw.server.model;


import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest{
    private Board board;
    private final Bookshelf bookshelf = new Bookshelf();

    private void printBoard() {
        for (int j = 0; j < board.getBoardMatrix().length; j++) {
            for (int k = 0; k < board.getBoardMatrix()[0].length; k++) {
                System.out.printf("%10s", board.getMatrixTile(j,k).getItemTileType().toString());
            }
            System.out.println(" ");
        }
    }

    @Test
    public void test2PlayersBoard() {
        try {
            board = new Board(2);
            printBoard();
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
            printBoard();
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
            printBoard();
            System.out.println("");
        } catch (InvalidParameterException ignored) {};
    }


    @Test
    public void testCheckRefillFalse() {
        Board board = new Board(2);

        ItemTile[][] boardItemTileMatrix = {
                {new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.EMPTY)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.FRAME), new ItemTile(ItemTileType.EMPTY)},
                {new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.EMPTY)}
        };

        board.setBoardMatrix(boardItemTileMatrix);
        assertFalse(board.checkRefill());

    }

    @Test
    public void testCheckRefillTrue1() {
        Board board = new Board(2);

        ItemTile[][] boardItemTileMatrix = {
                {new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.EMPTY)},
                {new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.FRAME), new ItemTile(ItemTileType.EMPTY)},
                {new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.EMPTY)}
        };

        board.setBoardMatrix(boardItemTileMatrix);
        assertTrue(board.checkRefill());

    }

    @Test
    public void testCheckRefillTrue2() {
        Board board = new Board(2);

        ItemTile[][] boardItemTileMatrix = {
                {new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.EMPTY)},
                {new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.FRAME), new ItemTile(ItemTileType.EMPTY)},
                {new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.FRAME), new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.EMPTY)},
                {new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.EMPTY)}
        };

        board.setBoardMatrix(boardItemTileMatrix);
        assertTrue(board.checkRefill());

    }

    @Test
    public void testSetters(){
        Board board = new Board(2);

        ArrayList<int[]> expected = new ArrayList<>();
        expected.add(new int[] {1, 2});
        expected.add(new int[] {3, 2});
        expected.add(new int[] {2, 2});

        ItemTile[][] boardItemTileMatrix = {
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.FRAME), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.FRAME)}
        };


        board.setBoardMatrix(boardItemTileMatrix);
        assertEquals(boardItemTileMatrix, board.getBoardMatrix());

        board.setNumOfPlayers(3);
        assertEquals(3, board.getNumOfPlayers());

        board.setCanBeSelectedCoordinates(expected);
        assertEquals(expected, board.getCanBeSelectedCoordinates());

        board.setSelectedCoordinates(expected);
        assertEquals(expected, board.getSelectedCoordinates());


    }




}