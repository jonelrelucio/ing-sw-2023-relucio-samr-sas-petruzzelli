package it.polimi.ingsw.client.view.cli.clicontroller;

import it.polimi.ingsw.client.view.cli.Const;
import it.polimi.ingsw.model.ItemTile.ItemTileType;

import java.util.ArrayList;
import java.util.Arrays;

public class ControllerPrint {

    private ItemTileType[][] boardMatrix;
    private String[] playerList;
    private ItemTileType[][][] bookshelfList;
    private ArrayList<int[]> canBeSelectedCoordinates;
    private ArrayList<int[]> selectedCoordinates;
    private String currentPlayer;

    public void init(ItemTileType[][] boardMatrix, String[] playerList, ItemTileType[][][] bookshelfList, ArrayList<int[]> canBeSelectedCoordinates, ArrayList<int[]> selectedCoordinates, String currentPlayer) {
        this.boardMatrix = boardMatrix;
        this.playerList = playerList;
        this.bookshelfList = bookshelfList;
        this.canBeSelectedCoordinates = canBeSelectedCoordinates;
        this.selectedCoordinates = selectedCoordinates;
        this.currentPlayer = currentPlayer;
    }

    private boolean containsCoordinates(ArrayList<int[]> list, int i, int j){
        int[] coordinates = new int[]{i, j};
        for (int[] array : list) {
            if (Arrays.equals(array, coordinates)) return true;
        }
        return false;
    }

    public void printBoard() {
        System.out.println("      Game Board with "+ playerList.length + " players");
        System.out.println("      ┌────────────────────────────────────────────┐");
        System.out.println("      │                                            │");
        for (int i = 1; i < boardMatrix.length-1; i++){
            System.out.print("      │");
            for (int j = 0; j < boardMatrix[0].length; j++) {
                if(boardMatrix[i][j] == ItemTileType.EMPTY) System.out.print(" "+ Const.TILE);
                else if (containsCoordinates( canBeSelectedCoordinates, i, j))
                    System.out.print(" "+Const.getHighlightedItemColor(boardMatrix[i][j])+Const.SELECTABLE_TILE +Const.RESET );
                else if (containsCoordinates( selectedCoordinates, i, j))
                    System.out.print(" "+Const.getHighlightedItemColor(boardMatrix[i][j])+Const.SELECTED_TILE +Const.RESET );
                else System.out.print(" "+Const.getItemColor(boardMatrix[i][j])+Const.TILE+Const.RESET);
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
        int numRows = bookshelfList[0].length;
        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
            for (int j = 0; j < bookshelfList.length; j++ ) {
                ItemTileType[] temp = bookshelfList[j][i];
                rows[i].append("      │");
                for (int k = 0; k < bookshelfList[j][0].length; k++) {
                    if (temp[k] == ItemTileType.EMPTY ) rows[i].append(Const.TILE + "│");
                    else rows[i].append(Const.getItemColor(temp[k])).append(Const.TILE).append(Const.RESET).append("│");
                }
                rows[i].append(" ");
            }
        }
        System.out.println("      " + getPlayerNickname());
        System.out.println("      " + getTopBorder(playerList.length));
        for (StringBuilder row : rows) {
            System.out.println(row.toString());
            if (rows[rows.length-1] != row) System.out.println("      " + getMidBorder(playerList.length));
        }
        System.out.println("      " + getBotBorder(playerList.length));
    }

    private String getPlayerNickname() {
        StringBuilder border = new StringBuilder();
        border.append(String.format("%-28s",playerList[0]));
        for (int i = 1; i < playerList.length; i++) {
            border.append(String.format("%-28s", playerList[i]));
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
    }


}
