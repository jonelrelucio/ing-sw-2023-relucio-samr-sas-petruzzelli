package it.polimi.ingsw.model;


import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

public class BoardTest{
    private Board board;

    @Test
    public void test2PlayersBoard(){
        try {
            board = new Board(2);
            board.printBoard();
            System.out.println("");
        } catch (InvalidParameterException ignored) {};

    }
    @Test
    public void test3PlayersBoard(){
        try {
            board = new Board(3);
            board.printBoard();
            System.out.println("");
        } catch (InvalidParameterException ignored) {};
    }
    @Test
    public void test4PlayersBoard(){
        try {
            board = new Board(4);
            board.printBoard();
            System.out.println("");
        } catch (InvalidParameterException ignored) {};
    }

}