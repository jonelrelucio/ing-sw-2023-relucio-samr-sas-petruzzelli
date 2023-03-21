package controller;

import junit.framework.TestCase;
import model.Board;
import org.junit.jupiter.api.Test;

import static model.ItemTileType.*;


public class BoardControllerTest extends TestCase {

    private BoardController bc = new BoardController(new Board(1) );

    @Test
    public void testIsAdjacentEmpty(){
        assertTrue(bc.isAdjacentEmpty(0, 1));
    }

    @Test
    public void testIsAdjacentEmptyOutOfBounds(){
        try {assertTrue(bc.isAdjacentEmpty(-1, -1));}
        catch (IndexOutOfBoundsException ex ) {}
    }





}