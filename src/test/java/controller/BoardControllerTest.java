package controller;

import it.polimi.ingsw.controller.BoardController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.ItemTile.ItemTile;
import java.util.ArrayList;

public class BoardControllerTest {

    private ItemTile[][][] matrix = {
            {
                    {new ItemTile(EMPTY), new ItemTile(EMPTY), new ItemTile(EMPTY), new ItemTile(EMPTY)},
                    {new ItemTile(EMPTY), new ItemTile(PLANT), new ItemTile(FRAME), new ItemTile(EMPTY)},
                    {new ItemTile(EMPTY), new ItemTile(TROPHY), new ItemTile(EMPTY), new ItemTile(EMPTY)},
                    {new ItemTile(BOOK), new ItemTile(CAT), new ItemTile(TROPHY), new ItemTile(EMPTY)},
            },
            {
                    {new ItemTile(EMPTY), new ItemTile(EMPTY), new ItemTile(EMPTY)},
                    {new ItemTile(EMPTY), new ItemTile(PLANT), new ItemTile(EMPTY)},
                    {new ItemTile(EMPTY), new ItemTile(TROPHY), new ItemTile(EMPTY)}
            }

    };
    private int[][][] expected = {
            {
                    {1, 1}, {1,2}, {2, 1}
            },
            {
                    {1, 1},
            },
            {
                    {2, 1}, {1, 2}
            },
            {

            }
    };



    @Test
    public void testCanBeSelectedTiles() {
        BoardController bc = new BoardController(matrix[0]);
        for (int i = 0; i < expected[0].length; i++){
            for (int j = 0; j < 2; j++) {
                assertEquals(expected[0][i][j], bc.getCanBeSelectedCoordinates().get(i)[j]);
            }
        }
    }
    @Test
    public void testCanBeSelectedTiles1() {
        BoardController bc = new BoardController(matrix[1]);
        for (int i = 0; i < expected[1].length; i++){
            for (int j = 0; j < 2; j++) {
                assertEquals(expected[1][i][j], bc.getCanBeSelectedCoordinates().get(i)[j]);
            }
        }
    }
    @Test
    public void testCanBeSelectedTiles2() {
        ArrayList<ItemTile> selectedTiles = new ArrayList<>();
        BoardController bc = new BoardController(matrix[0]);
        bc.selectTile(1, 1);
        for (int i = 0; i < expected[2].length; i++){
            for (int j = 0; j < 2; j++) {
                assertEquals(expected[2][i][j], bc.getCanBeSelectedCoordinates().get(i)[j]);
            }
        }
    }
    @Test
    public void testCanBeSelectedTiles3() {
        ArrayList<ItemTile> selectedTiles = new ArrayList<>();
        BoardController bc = new BoardController(matrix[0]);
        bc.selectTile(1, 1);
        bc.selectTile(1, 2);
        for (int i = 0; i < expected[3].length; i++){
            for (int j = 0; j < 2; j++) {
                assertEquals(expected[3][i][j], bc.getCanBeSelectedCoordinates().get(i)[j]);
            }
        }
    }
    @Test
    public void testPopSelectedTile() {
        ArrayList<ItemTile> selectedTiles = new ArrayList<>();
        BoardController bc = new BoardController(matrix[0]);
        bc.selectTile(1, 1);
        bc.selectTile(1, 2);
        assertEquals(1, bc.getSelectedCoordinates().get(0)[0]);
        assertEquals(1, bc.getSelectedCoordinates().get(0)[1]);
        assertEquals(1, bc.getSelectedCoordinates().get(1)[0]);
        assertEquals(2, bc.getSelectedCoordinates().get(1)[1]);
        bc.popSelectedTile(new int[] {1, 2});
    }


    @Test
    public void testGetSelectedTile() {
        BoardController bc = new BoardController(matrix[0]);
        bc.selectTile(1, 1);
        bc.selectTile(1, 2);
        ArrayList<ItemTile> expected = new ArrayList<>();
        expected.add(bc.getBoardMatrix()[1][1]);
        expected.add(bc.getBoardMatrix()[1][2]);
        ArrayList<ItemTile> selectedTile = bc.getSelectedItemTiles();
        for (int i = 0; i < expected.size(); i++) {
            assertSame(expected.get(i), selectedTile.get(i));
        }
    }











}