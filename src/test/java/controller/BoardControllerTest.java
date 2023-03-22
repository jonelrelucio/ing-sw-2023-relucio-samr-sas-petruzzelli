package controller;

import junit.framework.TestCase;
import model.Board;
import model.ItemTile.ItemTile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static model.ItemTile.ItemTileType.*;


public class BoardControllerTest extends TestCase {

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

    private BoardController bc = new BoardController(new Board(2));


    @Test
    public void testCanBeSelectedTiles() {
        bc.getBoard().setBoardMatrix(matrix[0]);
        bc.updateCanBeSelectedTiles();
        for (int i = 0; i < expected[0].length; i++){
            for (int j = 0; j < 2; j++) {
                assertEquals(expected[0][i][j], bc.getCanBeSelectedTiles().get(i)[j]);
            }
        }
    }
    @Test
    public void testCanBeSelectedTiles1() {
        bc.getBoard().setBoardMatrix(matrix[1]);
        bc.updateCanBeSelectedTiles();
        for (int i = 0; i < expected[1].length; i++){
            for (int j = 0; j < 2; j++) {
                assertEquals(expected[1][i][j], bc.getCanBeSelectedTiles().get(i)[j]);
            }
        }
    }
    @Test
    public void testCanBeSelectedTiles2() {
        ArrayList<ItemTile> selectedTiles = new ArrayList<>();
        bc.getBoard().setBoardMatrix(matrix[0]);
        bc.updateCanBeSelectedTiles();
        bc.selectTile(1, 1);
        for (int i = 0; i < expected[2].length; i++){
            for (int j = 0; j < 2; j++) {
                assertEquals(expected[2][i][j], bc.getCanBeSelectedTiles().get(i)[j]);
            }
        }
    }
    @Test
    public void testCanBeSelectedTiles3() {
        ArrayList<ItemTile> selectedTiles = new ArrayList<>();
        bc.getBoard().setBoardMatrix(matrix[0]);
        bc.updateCanBeSelectedTiles();
        bc.selectTile(1, 1);
        bc.selectTile(1, 2);
        for (int i = 0; i < expected[3].length; i++){
            for (int j = 0; j < 2; j++) {
                assertEquals(expected[3][i][j], bc.getCanBeSelectedTiles().get(i)[j]);
            }
        }
    }
    @Test
    public void testPopSelectedTile() {
        ArrayList<ItemTile> selectedTiles = new ArrayList<>();
        bc.getBoard().setBoardMatrix(matrix[0]);
        bc.updateCanBeSelectedTiles();
        bc.selectTile(1, 1);
        bc.selectTile(1, 2);
        assertEquals(1, bc.getSelectedTile().get(0)[0]);
        assertEquals(1, bc.getSelectedTile().get(0)[1]);
        assertEquals(1, bc.getSelectedTile().get(1)[0]);
        assertEquals(2, bc.getSelectedTile().get(1)[1]);
        bc.popSelectedTile(new int[] {1, 2});
    }













}