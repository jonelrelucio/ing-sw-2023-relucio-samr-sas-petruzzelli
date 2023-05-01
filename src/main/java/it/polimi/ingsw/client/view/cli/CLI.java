package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.cli.clicontroller.Utility;
import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.distributed.events.NewGame;
import it.polimi.ingsw.distributed.events.ViewEvents.WaitingForPlayersEvent;
import it.polimi.ingsw.distributed.events.ViewEvents.WaitingToJoin;
import it.polimi.ingsw.distributed.events.modelEvents.AddPlayer;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.util.Observable;

import java.util.Scanner;

public class CLI extends Observable<GameEvent> implements View {

    GameModel model;
    static Scanner s = new Scanner(System.in);

    @Override
    public void newGame() {
        System.out.println("Welcome to Myshelfie");
        System.out.println("1 <- Create Game");
        System.out.println("2 <- Exit game");
        int input;
        do {
            input = Utility.getNumInput();
            if (input <= 0 || input >= 4 ) System.out.println(Const.RED_BOLD_BRIGHT +"Invalid input. Enter a number from 1 to 2."+Const.RESET);
        }   while(input <= 0 || input >= 4  );
        switch (input) {
            case 1 -> createNewGame();
            case 3 -> System.exit(0);
        }
    }

    @Override
    public void joinGame() {
        System.out.println("A Game lobby is already running.");
        System.out.println("1 <- Join Game");
        System.out.println("2 <- Exit game");

        int input;
        do {
            input = Utility.getNumInput();
            if (input <= 0 || input >= 4 ) System.out.println(Const.RED_BOLD_BRIGHT +"Invalid input. Enter a number from 1 to 2."+Const.RESET);
        }   while(input <= 0 || input >= 4  );
        switch (input) {
            case 1 -> joinExistingGame();
            case 3 -> System.exit(0);
        }
    }

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

    public void createNewGame() {
        String username = askPlayerName();
        int numOfPlayers = askNumOfPlayers();
        System.out.println("Starting a new Game. Contacting server...");
        setChanged();
        notifyObservers(new NewGame(numOfPlayers, username));
    }

    public void joinExistingGame() {
        String username = askPlayerName();
        setChanged();
        notifyObservers(new AddPlayer(username));
    }

    public void waitForPlayers(GameEvent x) {
        if ( !(x instanceof WaitingForPlayersEvent event)  ) throw new IllegalArgumentException("Game Event is not of instance WaitingForPlayerEvent");
        if (event.remainingPlayers() == 0) {
            System.out.println("Starting new Game");
        }
        String multiple = event.remainingPlayers() == 1? "" : "s";
        if (event.isFirstPlayer()) System.out.println("You are the first player to connect...");
        System.out.printf("\rWaiting for %s more player%s", event.remainingPlayers(), multiple );
    }

    private void waitingToJoin(GameEvent x) {
        if ( !(x instanceof WaitingToJoin event)  ) throw new IllegalArgumentException("Game Event is not of instance WaitingToJoinEvent");
        if ( event.isWaitingToJoin() ) System.out.println("Game lobby is full. Wait...");
        else joinExistingGame();
    }


    @Override
    public void handleViewEvent( GameEvent event) {
        String eventName = event.getEventName();
        switch (eventName){
            case "WAITING_PLAYERS" -> waitForPlayers(event);
            case "WAITING_TO_JOIN" -> waitingToJoin(event);
        }
    }








    public static void main(String[] args) {

        /*final ItemTile[][] groupOfTwo =
                {       {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.TROPHY), new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.GAME)},
                          {new ItemTile(ItemTileType.PLANT),  new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME)},
                        {new ItemTile(ItemTileType.PLANT),  new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.FRAME)}};


        final ItemTile[][] bookshelf2 =
                {       {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.EMPTY),    new ItemTile(ItemTileType.TROPHY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.GAME),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.TROPHY),    new ItemTile(ItemTileType.GAME),     new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.CAT)}};

        final ItemTile[][] bookshelf1 =
                {       {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.TROPHY)},
                        {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.PLANT),    new ItemTile(ItemTileType.BOOK)},
                        {new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK)},
                        {new ItemTile(ItemTileType.CAT),  new ItemTile(ItemTileType.PLANT),    new ItemTile(ItemTileType.PLANT),    new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.PLANT)},
                        {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.PLANT),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME)}};

        final ItemTile[][] eightPieces =
                {       {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.CAT)},
                        {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.BOOK)},
                        {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),     new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK)},
                        {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.CAT)},
                        {new ItemTile(ItemTileType.TROPHY), new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK)}};



        int numOfPlayers = 4;
        CLI.startingScreen();
        GameModel model = new GameModel(numOfPlayers);
        CLI view = new CLI(model);
        Player player1 = new Player("Jonel", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers), model.getBoard());
        player1.getBookshelf().setBookshelfMatrix(groupOfTwo);
        Player player2 = new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers), model.getBoard());
        player2.getBookshelf().setBookshelfMatrix(bookshelf2);
        Player player3 = new Player("Dalila", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers), model.getBoard());
        player3.getBookshelf().setBookshelfMatrix(eightPieces);
        Player player4 = new Player("Lucian", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers), model.getBoard());
        player4.getBookshelf().setBookshelfMatrix(bookshelf1);

        model.addNewPlayer(player1);
        model.addNewPlayer(player2);
        model.addNewPlayer(player3);
        model.addNewPlayer(player4);

        player1.selectCoordinates(new int[]{2, 4});
        player1.selectCoordinates(new int[]{3, 4});
        player1.pickSelectedItemTiles();

        view.printAll();*/

    }

}
