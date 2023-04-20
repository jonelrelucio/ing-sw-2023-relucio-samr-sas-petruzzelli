package it.polimi.ingsw.view;

import java.util.Scanner;

public class CLI {
    Scanner s = new Scanner(System.in);

    public void startingScreen() {
        System.out.println(Const.title);
        int input = getStartingInput();
        switch (input) {
            case 1 -> newGame();
            case 2 -> joinGame();
            case 3 -> closeGame();
        }
    }

    private int getStartingInput(){
        try {
            int input = Integer.parseInt(s.nextLine());
            if (input > 0 && input < 4) return input;
            else {
                System.out.println("Invalid input. Enter a number from 1 to 3.");
                return getStartingInput();
            }
        }
        catch (NumberFormatException ex) {
            System.out.println("Invalid input. Enter a number from 1 to 3.");
            return getStartingInput();
        }
    }

    public void newGame() {
        System.out.println("Starting a new Game. Contacting server...");
    }

    public void joinGame() {
        System.out.println("Joining game. Contacting server...");
    }

    public void closeGame() {
        System.out.println("Closing game.");
    }



    public int[] askPlayerSelectedTiles() {
        System.out.println("Select tile from board: ");
        return new int[]{Integer.parseInt(s.nextLine())};
    }

    public static void main(String[] args) {
        CLI view = new CLI();
        view.startingScreen();
    }


}
