package it.polimi.ingsw.client.view.cli.clicontroller;

import it.polimi.ingsw.controller.events.NewGame;
import it.polimi.ingsw.util.Observable;

import java.util.Scanner;

public class ControllerNewGame extends Observable<NewGame> {

    static Scanner s = new Scanner(System.in);


    private int getNumInput(){
        try {
            return Integer.parseInt(s.nextLine());
        }
        catch (NumberFormatException ex) {
            System.out.println("Invalid input. Enter a number.");
            return getNumInput();
        }
    }

    private String askPlayerName() {
        String username;
        System.out.print("Please choose your username: ");
        do {
            s = new Scanner(System.in);
            username = s.nextLine();
            if (username.length() < 3 || username.isBlank()) System.out.println("Invalid username, try again...");
        }   while( username.length() < 3 || username.isBlank() );
        return username;
    }

    private int askNumOfPlayers() {
        int numOfPlayers;
        System.out.print("Please choose number of players: ");
        do {
            numOfPlayers = getNumInput();
            if (numOfPlayers <= 1 || numOfPlayers >= 5 ) System.out.println("Invalid input. Only 2 to 4 Players can play the game.");
        }   while(numOfPlayers <= 1 || numOfPlayers >= 5  );
        return numOfPlayers;
    }

    public void run() {
        String username = askPlayerName();
        int numOfPlayers = askNumOfPlayers();
        System.out.println("Starting a new Game. Contacting server...");
        setChanged();
        notifyObservers(new NewGame(numOfPlayers, username));
        Utility.printLoading();
    }



}
