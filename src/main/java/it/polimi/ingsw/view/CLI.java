package it.polimi.ingsw.view;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.bag.PersonalGoalCardBag;

import java.util.Scanner;

public class CLI {
    static Scanner s = new Scanner(System.in);
    GameModel model;

    public CLI(GameModel model) {
        this.model = model;
    }

    public static void startingScreen() {
        System.out.println(Const.title);
        int input;
        do {
            input = getNumInput();
            if (input <= 0 || input >= 4 ) System.out.println(Const.RED_BOLD_BRIGHT +"Invalid input. Enter a number from 1 to 3."+Const.RESET);
        }   while(input <= 0 || input >= 4  );
        switch (input) {
            case 1 -> newGame();
            case 2 -> joinGame();
            case 3 -> closeGame();
        }
    }

    private static int getNumInput(){
        try {
            return Integer.parseInt(s.nextLine());
        }
        catch (NumberFormatException ex) {
            System.out.println("Invalid input. Enter a number.");
            return getNumInput();
        }
    }

    public static void newGame() {
        System.out.println("Starting a new Game. Contacting server...");
    }

    public static void joinGame() {
        System.out.println("Joining game. Contacting server...");
    }

    public static void closeGame() {
        System.out.println("Closing game.");
    }

    public static void askNumOfPlayers() {
        int input;
        do {
            input = getNumInput();
            if (input <= 1 || input >= 5 ) System.out.println("Invalid input. Only 2 to 4 Players can play the game.");
        }   while(input <= 1 || input >= 5  );
    }


    public void printBoard() {
        ItemTile[][] boardMatrix = model.getBoard().getBoardMatrix();
        System.out.println("Printing Board for "+model.getNumOfPlayer()+" players...");
        System.out.println(" ");
        System.out.println("      ┌────────────────────────────────────────────┐");
        System.out.println("      │                                            │");
        for (int i = 1; i < boardMatrix.length-1; i++){
            System.out.print("      │");
            for (int j = 0; j < boardMatrix[0].length; j++) {
                if(boardMatrix[i][j].isEmpty())System.out.print(" "+Const.TILE);
                else System.out.print("│"+Const.getItemColor(boardMatrix[i][j].getItemTileType())+Const.TILE+Const.RESET);
            }
            System.out.println("│");
            System.out.println("      │                                            │");
        }
        System.out.println("      └────────────────────────────────────────────┘");
        System.out.println(" ");
    }

    public void printPersonalGoalCard(Player player) {
        ItemTile[][] pgcMatrix = player.getPersonalGoalCard().getPersonalGoalCardMatrix();
        System.out.println("Printing personal goal card for "+player.getNickname());
        System.out.println(" ");
        System.out.println("      ┌───┬───┬───┬───┬───┐");
        for (ItemTile[] matrix : pgcMatrix) {
            System.out.print("      │");
            for (int j = 0; j < pgcMatrix[0].length; j++) {
                if (matrix[j].isEmpty()) System.out.print( Const.TILE + "│");
                else System.out.print(Const.getItemColor(matrix[j].getItemTileType()) + Const.TILE + Const.RESET + "│");
            }
            System.out.println(" ");
            if (pgcMatrix[pgcMatrix.length-1] != matrix)  System.out.println("      ├───┼───┼───┼───┼───┤");
            else System.out.println("      └───┴───┴───┴───┴───┘");
        }
    }

    public int[] askPlayerSelectedTiles() {
        System.out.println("Select tile from board: ");
        return new int[]{Integer.parseInt(s.nextLine())};
    }

    public static void main(String[] args) {
        int numOfPlayers = 4;
        CLI.startingScreen();
        GameModel model = new GameModel(numOfPlayers);
        CLI view = new CLI(model);
        view.printBoard();
        PersonalGoalCard personalGoalCard = PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers);
        Player player = new Player("Jonel", personalGoalCard, model.getBoard());
        view.printPersonalGoalCard(player);

    }


}
