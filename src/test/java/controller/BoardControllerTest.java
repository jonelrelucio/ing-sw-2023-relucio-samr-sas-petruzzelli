package controller;

import junit.framework.TestCase;
import model.Board;
import model.ItemTile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static model.ItemTileType.*;


public class BoardControllerTest extends TestCase {

    ItemTile[][] matrix = {
            {new ItemTile(EMPTY), new ItemTile(EMPTY), new ItemTile(PLANT), new ItemTile(GAME)},
            {new ItemTile(GAME), new ItemTile(PLANT), new ItemTile(FRAME), new ItemTile(EMPTY)},
            {new ItemTile(EMPTY), new ItemTile(TROPHY), new ItemTile(EMPTY), new ItemTile(EMPTY)},
            {new ItemTile(BOOK), new ItemTile(CAT), new ItemTile(TROPHY), new ItemTile(GAME)},
    };
    ArrayList<int[]> expected = new ArrayList<>();


    private Board board = new Board(2);
    private BoardController bc = new BoardController(board);

    @Test
    public void testCanBeSelectedTiles() {
        bc.getBoard().setBoardMatrix(matrix);
        Collections.addAll(expected, new int[][]{{0, 2}, {0, 3}, {1, 0}, {1, 1}, {1, 2},{2, 1}, {2, 1}, {3, 0}, {3, 1}, {3, 2}, {3, 3}});
        System.out.print(expected);
        ArrayList<int[]> canBeSelected = new ArrayList<int[]>();
        canBeSelected = bc.getCanBeSelectedTiles();
        System.out.println(canBeSelected);
    }













    private BoardController bc1 = new BoardController(new Board(1) );
    @Test
    public void testIsAdjacentEmpty(){
        assertTrue(bc1.isAdjacentEmpty(0, 1));
    }

    @Test
    public void testIsAdjacentEmptyOutOfBounds(){
        try {assertTrue(bc1.isAdjacentEmpty(-1, -1));}
        catch (IndexOutOfBoundsException ex ) {}
    }




}