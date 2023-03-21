package model;


import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest{
    private Board board;

    @Test
    public void test1PlayerBoard(){
        try {
            board = new Board(1);
            board.printBoard();
        } catch (InvalidParameterException ignored) {};
    }
    @Test
    public void test2PlayersBoard(){
        try {
            board = new Board(2);
            board.printBoard();
        } catch (InvalidParameterException ignored) {};

    }
    @Test
    public void test3PlayersBoard(){
        try {
            board = new Board(3);
            board.printBoard();
        } catch (InvalidParameterException ignored) {};
    }
    @Test
    public void test4PlayersBoard(){
        try {
            board = new Board(4);
            board.printBoard();

        } catch (InvalidParameterException ignored) {};
    }
    @Test
    public void test5PlayersBoard(){
        try {
            board = new Board(5);
            board.printBoard();
        } catch (InvalidParameterException ignored) {};
    }

    @Test
    public void testGetSelectedTile() {
        Board b = new Board(2);
        ItemTile[][] board = new ItemTile[][] {
                {new ItemTile(ItemTileType.PLANT), new ItemTile(ItemTileType.GAME), new ItemTile(ItemTileType.PLANT)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.TROPHY), new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.BOOK)}
        };
        b.setBoardMatrix(board);
        System.out.println("\nInit Board:");
        b.printBoard();
        ArrayList<int[]> coordinates = new ArrayList<>();
        coordinates.add(new int[]{0, 0});
        coordinates.add(new int[]{0, 1});
        coordinates.add(new int[]{0, 2});
        ArrayList<ItemTile> expected = new ArrayList<>();
        expected.add(board[0][0]);
        expected.add(board[0][1]);
        expected.add(board[0][2]);
        ArrayList<ItemTile> selectedTile = b.getSelectedTile(coordinates);
        for (int i = 0; i < expected.size(); i++) {
            assertTrue(expected.get(i) == selectedTile.get(i));
        }
        System.out.println("\nUpdated board");
        b.printBoard();
    }

}