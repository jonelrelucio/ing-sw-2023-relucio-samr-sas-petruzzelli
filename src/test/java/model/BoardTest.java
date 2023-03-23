package model;


import model.ItemTile.ItemTile;
import model.ItemTile.ItemTileType;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;

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


}