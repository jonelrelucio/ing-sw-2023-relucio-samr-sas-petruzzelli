package it.polimi.ingsw.view;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.ItemTile.ItemTileType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.bag.PersonalGoalCardBag;
import it.polimi.ingsw.model.util.Utility;

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
        printLoading();
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
        System.out.println("      Game Board with "+model.getNumOfPlayer() + " players");
        System.out.println("      ┌────────────────────────────────────────────┐");
        System.out.println("      │                                            │");
        for (int i = 1; i < boardMatrix.length-1; i++){
            System.out.print("      │");
            for (int j = 0; j < boardMatrix[0].length; j++) {
                if(boardMatrix[i][j].isEmpty())System.out.print(" "+Const.TILE);
                else if (Utility.containsCoordinates( model.getBoard().getCanBeSelectedCoordinates(), i, j))
                    System.out.print(" "+Const.getHighlightedItemColor(boardMatrix[i][j].getItemTileType())+Const.SELECTABLE_TILE +Const.RESET );
                else if (Utility.containsCoordinates( model.getBoard().getSelectedCoordinates(), i, j))
                    System.out.print(" "+Const.getHighlightedItemColor(boardMatrix[i][j].getItemTileType())+Const.SELECTED_TILE +Const.RESET );
                else System.out.print(" "+Const.getItemColor(boardMatrix[i][j].getItemTileType())+Const.TILE+Const.RESET);
            }
            System.out.println("│");
            System.out.println("      │                                            │");
        }
        System.out.println("      └────────────────────────────────────────────┘");
    }

    public void printMatrix(ItemTile[][] matrix) {
        System.out.println("      ┌───┬───┬───┬───┬───┐");
        for (ItemTile[] temp : matrix) {
            System.out.print("      │");
            for (int j = 0; j < matrix[0].length; j++) {
                if (temp[j].isEmpty()) System.out.print( Const.TILE + "│");
                else System.out.print(Const.getItemColor(temp[j].getItemTileType()) + Const.TILE + Const.RESET + "│");
            }
            System.out.println(" ");
            if (matrix[matrix.length-1] != temp)  System.out.println("      ├───┼───┼───┼───┼───┤");
            else System.out.println("      └───┴───┴───┴───┴───┘");
        }
    }

    public void printPersonalGoal(Player player){
        System.out.println("      " + player.getNickname() + "'s Personal Goal Card:");
        printMatrix(player.getPersonalGoalCard().getPersonalGoalCardMatrix());
    }

    public void printBookShelf(Player player) {
        System.out.println("      " + player.getNickname() + "'s Bookshelf:");
        printMatrix(player.getBookshelf().getBookshelfMatrix());
    }

    public void printBookshelves() {
        int numRows = model.getPlayerList().get(0).getBookshelf().getBookshelfMatrix().length;
        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
            for (Player person : model.getPlayerList()) {
                ItemTile[] temp = person.getBookshelf().getBookshelfMatrix()[i];
                rows[i].append("      │");
                for (int j = 0; j < person.getBookshelf().getBookshelfMatrix()[0].length; j++) {
                    if (temp[j].isEmpty()) rows[i].append(Const.TILE + "│");
                    else rows[i].append(Const.getItemColor(temp[j].getItemTileType())).append(Const.TILE).append(Const.RESET).append("│");
                }
                rows[i].append(" ");
            }
        }
        System.out.println("      " + getPlayerNickname(model.getPlayerList().size()));
        System.out.println("      " + getTopBorder(model.getPlayerList().size()));
        for (StringBuilder row : rows) {
            System.out.println(row.toString());
            if (rows[rows.length-1] != row) System.out.println("      " + getMidBorder(model.getPlayerList().size()));
        }
        System.out.println("      " + getBotBorder(model.getPlayerList().size()));
    }

    private String getPlayerNickname(int numMatrices) {
        StringBuilder border = new StringBuilder();
        border.append(String.format("%-28s", model.getPlayerList().get(0).getNickname()));
        for (int i = 1; i < model.getPlayerList().size(); i++) {
            border.append(String.format("%-28s", model.getPlayerList().get(i).getNickname()));
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
        printBoard();
        printBookshelves();
    }

    public static void printLoading()  {
        int index = 0;
        StringBuilder string = new StringBuilder("#");
        String missing = "                                  ";
        while (index <= 33) {
            System.out.printf("\rLoading [ "+string+missing+"]%d%%", (int)index*100/33);
            string.append("#");
            missing = missing.substring(0, 33-index);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            index++;
        }
        System.out.println();
    }

    public int[] askPlayerSelectedTiles() {
        System.out.println("Select tile from board: ");
        return new int[]{Integer.parseInt(s.nextLine())};
    }
















    public static void main(String[] args) {

        final ItemTile[][] groupOfTwo =
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
        // player1.selectCoordinates(new int[]{3, 4});
        // player1.pickSelectedItemTiles();

        view.printAll();
    }

}