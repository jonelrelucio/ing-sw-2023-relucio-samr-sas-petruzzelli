package it.polimi.ingsw.client.view.cli.clicontroller;

import it.polimi.ingsw.client.view.cli.Const;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.util.Utility;

public class ControllerPrint {

    GameModel model;

    public ControllerPrint(GameModel model){
        this.model = model;
    }

    public void printBoard() {
        ItemTile[][] boardMatrix = model.getBoard().getBoardMatrix();
        System.out.println("      Game Board with "+model.getNumOfPlayer() + " players");
        System.out.println("      ┌────────────────────────────────────────────┐");
        System.out.println("      │                                            │");
        for (int i = 1; i < boardMatrix.length-1; i++){
            System.out.print("      │");
            for (int j = 0; j < boardMatrix[0].length; j++) {
                if(boardMatrix[i][j].isEmpty())System.out.print(" "+ Const.TILE);
                else if (it.polimi.ingsw.model.util.Utility.containsCoordinates( model.getBoard().getCanBeSelectedCoordinates(), i, j))
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

}
