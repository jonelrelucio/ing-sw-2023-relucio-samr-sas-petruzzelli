package it.polimi.ingsw.client.view.cli;

    import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import it.polimi.ingsw.distributed.events.controllerEvents.MessageEvent;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import it.polimi.ingsw.util.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static it.polimi.ingsw.distributed.events.controllerEvents.Event.*;

public class CLI extends Observable<MessageEvent> implements View, Runnable {

    private GameModelView gameModelView;
    static Scanner s = new Scanner(System.in);
    private String thisUsername;


    @Override
    public void run() {
        new Thread(() -> {
            printAll();
            newTurn();
        }).start();
    }

    public void newTurn(){
        if (gameModelView.getCurrentPlayer().equals(thisUsername)){
            System.out.println("It's your turn.");
            selectTile();
            reorderTiles();
            selectColumn();
        }

    }

    private void selectColumn() {
        printSelectedTiles();
    }


    private void reorderTiles() {
    }

    public int getNumInput(){
        try {
            return Integer.parseInt(s.nextLine());
        }
        catch (NumberFormatException ex) {
            System.out.println("Invalid input. Enter a number.");
            return getNumInput();
        }
    }



    @Override
    public String askUsername() {
        String username;
        System.out.print("Please choose your username: ");
        do {
            s = new Scanner(System.in);
            username = s.nextLine();
            if (username.length() < 3 || username.isBlank()) System.out.println("Invalid username, try again...");
        }   while( username.length() < 3 || username.isBlank() );
        return username;
    }


    @Override
    public int askMaxNumOfPlayers() {
        int maxNumOfPlayers;
        System.out.println("Please choose maximum number of players (from 2 to 4 players can join):");
        do {
            s = new Scanner(System.in);
            maxNumOfPlayers = getNumInput();
            if (maxNumOfPlayers < 2 || maxNumOfPlayers > 4) System.out.println("Only from 2 to 4 players can join. Selected a number again:");
        }   while( maxNumOfPlayers < 2 || maxNumOfPlayers > 4 );
        return maxNumOfPlayers;
    }

    @Override
    public void printMessage(String string) {
        System.out.println(string);
    }

    @Override
    public void update(GameModelView gameModelView) {
        this.gameModelView = gameModelView;
    }

    @Override
    public void setThisUsername(String thisUsername) {
        this.thisUsername = thisUsername;
    }


    private void selectTile() {
        String input;
        System.out.println("The Dotted spots on the board are the tiles that can be selected.");
        printCanBeSelectedTiles();
        int x, y;
        do {
            x = -1;
            y = -1;
            System.out.println("To select a tile enter the coordinates: x y");
            input = s.nextLine();
            String[] coordinates = input.split(" ");
            if (coordinates.length != 2 || isNumeric(coordinates[0]) || isNumeric(coordinates[1])) {
                System.out.println("Invalid coordinates, try again...");
                continue;
            }
            x = Integer.parseInt(coordinates[0]);
            y = Integer.parseInt(coordinates[1]);
        } while (x < 0 || y < 0);
        setChangedAndNotifyObservers(new MessageEvent(SELECT_COORDINATES, input));
        printAll();
        if  (gameModelView.getSelectedCoordinates().size() == 3 || gameModelView.getCanBeSelectedCoordinates().size() == 0) pickTiles();
        else if ( askIfSelectAgain() ) selectTile();
        else pickTiles();
    }

    private void pickTiles() {
        System.out.println("You picked the tiles from the board");
        setChangedAndNotifyObservers(new MessageEvent(PICK_TILES, " "));
    }

    private boolean askIfSelectAgain() {
        String answer;
        System.out.println("Do you want to select another coordinate? yes no");
        do {
            answer = s.nextLine();
            if (!answer.equals("yes") && !answer.equals("no") ) System.out.println("Please select yes or no");
        } while( !answer.equals("yes") && !answer.equals("no"));
        return answer.equals("yes");
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private void setChangedAndNotifyObservers(MessageEvent arg) {
        setChanged();
        notifyObservers(arg);
    }




    /***************************************************************************************************************/



    private void printSelectedTiles() {

        System.out.println("These are the tiles you have selected:");
        System.out.println(" ");
        int size = 5;

        System.out.print("      ┌");
        for (int i = 0; i < size; i++){
            if ( i != size-1) System.out.print("───┬");
            else System.out.print("───┐");
        }
        System.out.print("\n      │");
        for (int i = 0; i < size ; i++){
            if ( i < gameModelView.getSelectedTiles().size() )System.out.print(Const.getItemColor(gameModelView.getSelectedTiles().get(i)) + Const.TILE + Const.RESET + "│");
            else System.out.print("   │");
        }
        System.out.print("\n      └");
        for (int i = 0; i < size; i++){
            if ( i != size-1) System.out.print("───┴");
            else System.out.print("───┘\n");
        }

    }



    private boolean containsCoordinates(ArrayList<int[]> list, int i, int j){
        int[] coordinates = new int[]{i, j};
        for (int[] array : list) {
            if (Arrays.equals(array, coordinates)) return true;
        }
        return false;
    }

    public void printBoard() {
        System.out.println("      Game Board with "+ gameModelView.getPlayerList().length + " players");
        System.out.println("      ┌────────────────────────────────────────────┐");
        System.out.println("      │                                            │");
        for (int i = 1; i < gameModelView.getBoardMatrix().length-1; i++){
            System.out.print("      │");
            for (int j = 0; j < gameModelView.getBoardMatrix().length; j++) {
                if(gameModelView.getBoardMatrix()[i][j] == ItemTileType.EMPTY) System.out.print(" "+ Const.TILE);
                else if (containsCoordinates( gameModelView.getCanBeSelectedCoordinates(), i, j))
                    System.out.print(" "+Const.getHighlightedItemColor(gameModelView.getBoardMatrix()[i][j])+Const.SELECTABLE_TILE +Const.RESET );
                else if (containsCoordinates(gameModelView.getSelectedCoordinates(), i, j))
                    System.out.print(" "+Const.getHighlightedItemColor(gameModelView.getBoardMatrix()[i][j])+Const.SELECTED_TILE +Const.RESET );
                else System.out.print(" "+Const.getItemColor(gameModelView.getBoardMatrix()[i][j])+Const.TILE+Const.RESET);
            }
            System.out.println("│");
            System.out.println("      │                                            │");
        }
        System.out.println("      └────────────────────────────────────────────┘");
    }

    public void printMatrix(ItemTileType[][] matrix) {
        System.out.println("      ┌───┬───┬───┬───┬───┐");
        for (ItemTileType[] temp : matrix) {
            System.out.print("      │");
            for (int j = 0; j < matrix[0].length; j++) {
                if (temp[j] == ItemTileType.EMPTY) System.out.print( Const.TILE + "│");
                else System.out.print(Const.getItemColor(temp[j]) + Const.TILE + Const.RESET + "│");
            }
            System.out.println(" ");
            if (matrix[matrix.length-1] != temp)  System.out.println("      ├───┼───┼───┼───┼───┤");
            else System.out.println("      └───┴───┴───┴───┴───┘");
        }
    }
//
//    public void printPersonalGoal(String player){
//        System.out.println("      " + player + "'s Personal Goal Card:");
//        printMatrix(player.getPersonalGoalCard().getPersonalGoalCardMatrix());
//    }

//    public void printBookShelf(String player) {
//        System.out.println("      " + player + "'s Bookshelf:");
//        printMatrix(player.getBookshelf().getBookshelfMatrix());
//    }

    public void printBookshelves() {
        int numRows = gameModelView.getBookshelfList()[0].length;
        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
            for (int j = 0; j < gameModelView.getBookshelfList().length; j++ ) {
                ItemTileType[] temp = gameModelView.getBookshelfList()[j][i];
                rows[i].append("      │");
                for (int k = 0; k < gameModelView.getBookshelfList()[j][0].length; k++) {
                    if (temp[k] == ItemTileType.EMPTY ) rows[i].append(Const.TILE + "│");
                    else rows[i].append(Const.getItemColor(temp[k])).append(Const.TILE).append(Const.RESET).append("│");
                }
                rows[i].append(" ");
            }
        }
        System.out.println("      " + getPlayerNickname());
        System.out.println("      " + getTopBorder(gameModelView.getBookshelfList().length));
        for (StringBuilder row : rows) {
            System.out.println(row.toString());
            if (rows[rows.length-1] != row) System.out.println("      " + getMidBorder(gameModelView.getBookshelfList().length));
        }
        System.out.println("      " + getBotBorder(gameModelView.getBookshelfList().length));
    }

    private String getPlayerNickname() {
        StringBuilder border = new StringBuilder();
        border.append(String.format("%-28s",gameModelView.getPlayerList()[0]));
        for (int i = 1; i < gameModelView.getBookshelfList().length; i++) {
            border.append(String.format("%-28s",gameModelView.getPlayerList()[i]));
        }
        return border.toString();
    }

    private String getTopBorder(int numMatrices) {
        StringBuilder border = new StringBuilder();
        border.append("┌───┬───┬───┬───┬───┐");
        for (int i = 1; i < numMatrices; i++) {
            border.append("       ┌───┬───┬───┬───┬───┐");
        }
        return border.toString();
    }

    private String getMidBorder(int numMatrices) {
        StringBuilder border = new StringBuilder();
        border.append("├───┼───┼───┼───┼───┤");
        for (int i = 1; i < numMatrices; i++) {
            border.append("       ├───┼───┼───┼───┼───┤");
        }
        return border.toString();
    }

    private String getBotBorder(int numMatrices) {
        StringBuilder border = new StringBuilder();
        border.append("└───┴───┴───┴───┴───┘");
        for (int i = 1; i < numMatrices; i++) {
            border.append("       └───┴───┴───┴───┴───┘");
        }
        return border.toString();
    }

    public void printAll(){
        System.out.println(" ");
        System.out.println(" ");
        printBoard();
        printBookshelves();
        System.out.println(" ");
    }


    public void printCanBeSelectedTiles() {
        System.out.println("Can be selected Tiles coordinates: ");
        for ( int[] coordinates : gameModelView.getCanBeSelectedCoordinates() ){
            System.out.printf(" [%d %d]", coordinates[0], coordinates[1]);
        }
        System.out.println(" ");
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
