package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.cli.clicontroller.ControllerPrint;
import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.distributed.events.ViewEvents.*;
import it.polimi.ingsw.distributed.events.controllerEvents.NewGameEvent;
import it.polimi.ingsw.distributed.events.controllerEvents.SelectCoordinatesEvent;
import it.polimi.ingsw.distributed.events.controllerEvents.StartGameEvent;
import it.polimi.ingsw.distributed.events.controllerEvents.AddPlayer;
import it.polimi.ingsw.util.Observable;

import java.util.Scanner;

public class CLI extends Observable<GameEvent> implements View, Runnable {

    ControllerPrint controllerPrint = new ControllerPrint();
    private String playerNickname;
    static Scanner s = new Scanner(System.in);
    private boolean isConnected = false;

    @Override
    public void run() {
        if (isConnected) {
            newGame();
        }
        else joinGame();
    }

    @Override
    public void isConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    @Override
    public void handleViewEvent( GameEvent event) {
        String eventName = event.getEventName();
        switch (eventName){
            case "WAITING_PLAYERS" -> waitForPlayers(event);
            case "WAITING_TO_JOIN" -> waitingToJoin(event);
            case "INIT_GAME" -> initPrint(event);
            case "NEW_TURN_EVENT" -> listenToPlayerCommands(event);
            case "UPDATE_CAN_BE_SELECTED" -> updateCanBeSelectedTiles(event);
        }
    }

    private void setPlayerNickname(String nickname) {
        this.playerNickname = nickname;
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

    @Override
    public void newGame() {
        String username = askPlayerName();
        int numOfPlayers = askNumOfPlayers();
        System.out.println("Contacting server...");
        setChangedAndNotifyObservers(new NewGameEvent(numOfPlayers, username));
    }

    @Override
    public void joinGame() {
        String username = askPlayerName();
        setChangedAndNotifyObservers(new AddPlayer(username));
    }

    private void listenToPlayerCommands(GameEvent x) {
        if (!(x instanceof NewTurnEvent event)  ) throw new IllegalArgumentException("Game Event is not of NewTurnEvent exception");
        String currentPlayer = controllerPrint.getCurrentPlayer();
        String command;
        if (currentPlayer.equals(event.getCurrentPlayer())) System.out.println("It's your turn!");
        System.out.println("Type: +help for available commands");
        do {
            s = new Scanner(System.in);
            command = s.nextLine();
            if (!command.startsWith("+")) System.out.println("Invalid command, try again...");
        }   while( !command.startsWith("+") );
        if ( currentPlayer.equals((event.getCurrentPlayer())) ) {
            if (command.equals("+selectTiles")) selectTile();
        }
        else if (command.equals("+help")) printHelp(currentPlayer);
        //else if (command.equals("+showPersonalGoalCard")) getPersonalGoalCard();
        //else if (command.equals("+showCanBeSelectedTiles")) controllerPrint.showAvailableTiles();
        //TODO EXIT
    }

    private void printHelp(String currentPlayer ){
        if (currentPlayer.equals(playerNickname)) {
            System.out.println("+selectTiles        selects from 1 to 3 available tiles from the board.");
        }
        System.out.println("+showPersonalGoalCard   prints own personal goal Card.");
        System.out.println("+showCanBeSelectedTiles prints the available tiles that can be selected.");
        //TODO
    }

    private void selectTile() {
        String input;
        System.out.println("The Dotted spots on the board are the tiles that can be selected.");
        controllerPrint.printCanBeSelectedTiles();
        int x, y;
        do {
            x = -1;
            y = -1;
            System.out.println("To select a tile enter the coordinates: x y");
            s = new Scanner(System.in);
            input = s.nextLine();
            String[] coordinates = input.split(" ");
            if (coordinates.length != 2 || isNumeric(coordinates[0]) || isNumeric(coordinates[1])) {
                System.out.println("Invalid coordinates, try again...");
                continue;
            }
            x = Integer.parseInt(coordinates[0]);
            y = Integer.parseInt(coordinates[1]);
        } while (x < 0 || y < 0);
        setChangedAndNotifyObservers(new SelectCoordinatesEvent(x, y));
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }


    private void updateCanBeSelectedTiles(GameEvent x) {
        if ( !(x instanceof UpdateCanBeSelectedTilesEvent event)  ) throw new IllegalArgumentException("Game Event is not of instance UpdateCanBeSelectedTile");
        controllerPrint.setCanBeSelectedCoordinates(event.getCanBeSelectedCoordinates());
        controllerPrint.setSelectedCoordinates(event.getSelectedCoordinates());
        controllerPrint.printAll();
        selectTile();
    }


    private void waitForPlayers(GameEvent x) {
        if ( !(x instanceof WaitingForPlayersEvent event)  ) throw new IllegalArgumentException("Game Event is not of instance WaitingForPlayerEvent");
        setPlayerNickname(event.getConnectedPlayer());
        if (event.remainingPlayers() == 0) {
            System.out.println(" ");
            setChangedAndNotifyObservers(new StartGameEvent());
        }
        else {
            String multiple = event.remainingPlayers() == 1? "" : "s";
            if (event.isFirstPlayer()) System.out.println("You are the first player to connect...");
            System.out.printf("\rWaiting for %s more player%s", event.remainingPlayers(), multiple );
        }
    }

    private void waitingToJoin(GameEvent x) {
        if ( !(x instanceof FullGameLobbyEvent event)  ) throw new IllegalArgumentException("Game Event is not of instance WaitingToJoinEvent");
        System.out.println("\rGame lobby is full. Please wait...");
    }

    private void initPrint(GameEvent x) {
        if ( !(x instanceof InitGame event)  ) throw new IllegalArgumentException("Game Event is not of instance WaitingToJoinEvent");
        controllerPrint.init(event.getBoardMatrix(), event.getPlayerList(), event.getBookshelfList(), event.getCanBeSelectedCoordinates(), event.getSelectedCoordinates(), event.getCurrentPlayer());
        controllerPrint.printAll();
    }

    private void setChangedAndNotifyObservers(GameEvent arg) {
        setChanged();
        notifyObservers(arg);
    }

    //public static void main(String[] args) {

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

    //}

}
