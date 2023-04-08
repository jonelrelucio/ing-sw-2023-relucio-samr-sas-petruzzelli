package it.polimi.ingsw.model;


import it.polimi.ingsw.model.ItemTile.ItemTile;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class BoardTest{
    private Board board;
    private final Bookshelf bookshelf = new Bookshelf();

    /**
     * Testing Board with 2 players
     */
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

    /**
     * Testing Board with 3 players
     */
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

    /**
     * Testing Board with 4 players
     */
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




//
//
//
//
//
//
//    @Test
//    public void testCanBeSelectedTiles() {
//        board = new Board()
//        BoardController bc = new BoardController(matrix[0]);
//        for (int i = 0; i < expected[0].length; i++){
//            for (int j = 0; j < 2; j++) {
//                assertEquals(expected[0][i][j], bc.getCanBeSelectedCoordinates().get(i)[j]);
//            }
//        }
//    }
//    @Test
//    public void testCanBeSelectedTiles1() {
//        BoardController bc = new BoardController(matrix[1]);
//        for (int i = 0; i < expected[1].length; i++){
//            for (int j = 0; j < 2; j++) {
//                assertEquals(expected[1][i][j], bc.getCanBeSelectedCoordinates().get(i)[j]);
//            }
//        }
//    }
//    @Test
//    public void testCanBeSelectedTiles21() {
//        ArrayList<ItemTile> selectedTiles = new ArrayList<>();
//        BoardController bc = new BoardController(matrix[0]);
//        bc.selectTile(new int[]{1, 1}, bookshelf);
//        for (int i = 0; i < expected[2].length; i++){
//            for (int j = 0; j < 2; j++) {
//                assertEquals(expected[2][i][j], bc.getCanBeSelectedCoordinates().get(i)[j]);
//            }
//        }
//    }
//    @Test
//    public void testCanBeSelectedTiles3() {
//        ArrayList<ItemTile> selectedTiles = new ArrayList<>();
//        BoardController bc = new BoardController(matrix[0]);
//        bc.selectTile(new int[]{1, 1}, bookshelf);
//        bc.selectTile(new int[]{1, 2}, bookshelf);
//        for (int i = 0; i < expected[3].length; i++){
//            for (int j = 0; j < 2; j++) {
//                assertEquals(expected[3][i][j], bc.getCanBeSelectedCoordinates().get(i)[j]);
//            }
//        }
//    }
//    @Test
//    public void testPopSelectedTile() {
//        ArrayList<ItemTile> selectedTiles = new ArrayList<>();
//        BoardController bc = new BoardController(matrix[0]);
//        bc.selectTile(new int[]{1, 1}, bookshelf);
//        bc.selectTile(new int[]{1, 2}, bookshelf);
//        assertEquals(1, bc.getSelectedCoordinates().get(0)[0]);
//        assertEquals(1, bc.getSelectedCoordinates().get(0)[1]);
//        assertEquals(1, bc.getSelectedCoordinates().get(1)[0]);
//        assertEquals(2, bc.getSelectedCoordinates().get(1)[1]);
//        bc.deselectTile(new int[] {1, 2});
//    }
//
//
//    @Test
//    public void testGetSelectedTile() {
//        BoardController bc = new BoardController(matrix[0]);
//        bc.selectTile(new int[]{1, 1}, bookshelf);
//        bc.selectTile(new int[]{1, 2}, bookshelf);
//        ArrayList<ItemTile> expected = new ArrayList<>();
//        expected.add(bc.getBoardMatrix()[1][1]);
//        expected.add(bc.getBoardMatrix()[1][2]);
//        ArrayList<ItemTile> selectedTile = bc.getSelectedItemTiles();
//        for (int i = 0; i < expected.size(); i++) {
//            assertSame(expected.get(i), selectedTile.get(i));
//        }
//    }
//



}