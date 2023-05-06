package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import it.polimi.ingsw.distributed.events.controllerEvents.MessageEvent;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import it.polimi.ingsw.util.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import static it.polimi.ingsw.distributed.events.ViewEvents.EventView.*;
import static it.polimi.ingsw.distributed.events.controllerEvents.EventController.*;

public class CLI extends Observable<MessageEvent> implements View, Runnable {
    private Scanner s ;
    private String thisUsername;
    private final HashMap<EventView, ViewEventHandler> viewEventHandlers;


    public CLI(){
        viewEventHandlers = new HashMap<>();
        viewEventHandlers.put(SELECT_COORDINATES_SUCCESS, new SelectedCoordinatesSuccess());
        viewEventHandlers.put(SELECT_COORDINATES_FAIL, new SelectedCoordinatesFail());
        viewEventHandlers.put(DESELECT_COORDINATES_SUCCESS, new DeselectCoordinatesSuccess());
        viewEventHandlers.put(DESELECT_COORDINATES_FAIL, new DeselectCoordinatesFail());
        viewEventHandlers.put(PICK_TILES_SUCCESS, new PickTiles());
        viewEventHandlers.put(NEW_ORDER_SUCCESS, new NewOrderSuccess());
        viewEventHandlers.put(NEW_ORDER_FAIL, new NewOrderFail());
        viewEventHandlers.put(SELECT_COLUMN_FAIL, new SelectColumnFail());
    }

    @Override
    public void run() {
    }

    @Override
    public void ViewEventHandler(GameModelView gameModelView, EventView eventView) {
        viewEventHandlers.get(eventView).performAction(this, gameModelView);
    }


    public void newTurn(GameModelView gameModelView){
        new Thread(() -> {
            s = new Scanner(System.in);
            printAll(gameModelView);
            System.out.printf("It's %s's turn.\n", gameModelView.getCurrentPlayer());
            listenToPlayer(gameModelView);
        }).start();
    }

        private void listenToPlayer(GameModelView gameModelView) {
            if ( isMyTurn(gameModelView)) {
                selectCoordinates(gameModelView);
            }
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
    public void setThisUsername(String thisUsername) {
        this.thisUsername = thisUsername;
    }


    private String getCoordinates(){
        String input;
        int x, y;
        do {
            x = -1;
            y = -1;
            System.out.println("Enter the coordinates: x y");
            input = s.nextLine();
            String[] coordinates = input.split(" ");
            if (coordinates.length != 2 || !isNumeric(coordinates[0]) || !isNumeric(coordinates[1])) {
                System.out.println("Invalid coordinates, try again...");
                continue;
            }
            x = Integer.parseInt(coordinates[0]);
            y = Integer.parseInt(coordinates[1]);
        } while (x < 0 || y < 0);
        return input;
    }


    public void selectCoordinates(GameModelView gameModelView) {
        System.out.println("Do you want to select a coordinate from the board? yes/no");
        if (askYesOrNo()){
            String coordinates;
            System.out.println("The Dotted spots on the board are the tiles that can be selected.");
            printCanBeSelectedCoordinates(gameModelView);
            coordinates = getCoordinates();
            setChangedAndNotifyObservers(new MessageEvent(SELECT_COORDINATES, coordinates));
        }
        else pickTiles();
    }


    public void askDeselectCoordinates(GameModelView gameModelView) {
        printSelectedCoordinates(gameModelView);
        System.out.println("Do you want to deselect coordinates? yes/no: ");
        if (askYesOrNo()) {
            String coordinates = getCoordinates();
            setChangedAndNotifyObservers(new MessageEvent(DESELECT_COORDINATES, coordinates));
        }
        else if  (gameModelView.getSelectedCoordinates().size() == 3 || gameModelView.getCanBeSelectedCoordinates().size() == 0) pickTiles();
        else selectCoordinates(gameModelView);
    }

    private void pickTiles() {
        setChangedAndNotifyObservers(new MessageEvent(PICK_TILES, " "));
    }

    public boolean askYesOrNo() {
        String answer;
        do {
            answer = s.nextLine();
            if (!answer.equals("yes") && !answer.equals("no") ) System.out.println("Please select yes or no");
        } while( !answer.equals("yes") && !answer.equals("no"));
        return answer.equals("yes");
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void askTileOrder(GameModelView gameModelView) {
        System.out.println("Do you want to order your Tiles? yes/no");
        if (!askYesOrNo()) selectColumn(gameModelView);
        else {
            System.out.println("Rearrange the tiles by setting a new array of indexes: ");
            System.out.println("(example of new order: 2 0 1)");
            String input = getTileOrder();
            setChangedAndNotifyObservers(new MessageEvent(NEW_ORDER, input));
        }
    }

    private String getTileOrder() {
        String input;
        boolean isValid;
        do {
            isValid = true;
            input = s.nextLine();
            String[] strArr = input.split(" ");
            int[] intArr = new int[strArr.length];
            for (String value : strArr) {
                if (!isNumeric(value)) {
                    isValid = false;
                    break;
                }
                if (Integer.parseInt(value) < 0) {
                    isValid = false;
                    break;
                }
            }
            if (!isValid) {
                System.out.println("Please enter valid numbers only.");
            }
        } while (!isValid);
        return input;
    }

    public void selectColumn(GameModelView gameModelView) {
        printBookshelves(gameModelView);
        System.out.printf("%s select the column where you want to put your tiles. ", gameModelView.getCurrentPlayer());
        System.out.println("Choose a number from 0 to 4: ");
        String input;
        String[] strArr;
        int x;
        do {
            x = -1;
            input = s.nextLine();
            strArr = input.split(" ");
            if ( !isNumeric(input) || strArr.length != 1) System.out.println("Invalid input. Try again: ");
            else {
                x = Integer.parseInt(input);
                if (x < 0 ) System.out.println("Invalid input. Try Again: ");
            }
        } while (!isNumeric(input) || strArr.length != 1 || x < 0);
        setChangedAndNotifyObservers(new MessageEvent(SELECT_COLUMN, input));
    }


    private void setChangedAndNotifyObservers(MessageEvent arg) {
        setChanged();
        notifyObservers(arg);
    }

    public boolean isMyTurn(GameModelView gameModelView){
        return gameModelView.getCurrentPlayer().equals(thisUsername);
    }


    /***************************************************************************************************************/



    public void printSelectedTiles(GameModelView gameModelView) {

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

    public void printBoard(GameModelView gameModelView) {
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

    public void printBookshelves(GameModelView gameModelView) {
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
        System.out.println("      " + getPlayerNickname(gameModelView));
        System.out.println("      " + getTopBorder(gameModelView.getBookshelfList().length));
        for (StringBuilder row : rows) {
            System.out.println(row.toString());
            if (rows[rows.length-1] != row) System.out.println("      " + getMidBorder(gameModelView.getBookshelfList().length));
        }
        System.out.println("      " + getBotBorder(gameModelView.getBookshelfList().length));
        System.out.println(" ");
    }

    private String getPlayerNickname(GameModelView gameModelView) {
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

    public void printAll(GameModelView gameModelView){
        System.out.println(" ");
        System.out.println(" ");
        printBoard(gameModelView);
        printBookshelves(gameModelView);
        System.out.println(" ");
    }


    public void printCanBeSelectedCoordinates(GameModelView gameModelView) {
        System.out.println("Can be selected Tiles coordinates: ");
        for ( int[] coordinates : gameModelView.getCanBeSelectedCoordinates() ){
            System.out.printf(" [%d %d]", coordinates[0], coordinates[1]);
        }
        System.out.println(" ");
    }

    public void printSelectedCoordinates(GameModelView gameModelView) {
        if (gameModelView.getSelectedCoordinates().isEmpty()) return;
        System.out.println("These are the coordinates that you have selected: ");
        for ( int[] coordinates : gameModelView.getSelectedCoordinates() ){
            System.out.printf(" [%d %d]", coordinates[0], coordinates[1]);
        }
        System.out.println(" ");
    }

}

interface ViewEventHandler {

    void performAction(CLI view, GameModelView gameModelView);
}

class SelectedCoordinatesFail implements ViewEventHandler {

    @Override
    public void performAction(CLI view, GameModelView gameModelView) {
        if( !view.isMyTurn(gameModelView)) return;
        view.printAll(gameModelView);
        System.out.println("The selected coordinates are not available");
        view.selectCoordinates(gameModelView);
    }

}

class SelectedCoordinatesSuccess implements ViewEventHandler {

    @Override
    public void performAction(CLI view, GameModelView gameModelView) {
        if( !view.isMyTurn(gameModelView)) return;
        view.printAll(gameModelView);
        view.askDeselectCoordinates(gameModelView);
    }
}

class DeselectCoordinatesSuccess implements ViewEventHandler{

    @Override
    public void performAction(CLI view, GameModelView gameModelView) {
        if( !view.isMyTurn(gameModelView)) return;
        view.printAll(gameModelView);
        view.selectCoordinates(gameModelView);
    }
}

class DeselectCoordinatesFail implements ViewEventHandler{

    @Override
    public void performAction(CLI view, GameModelView gameModelView) {
        if( !view.isMyTurn(gameModelView)) return;
        view.printAll(gameModelView);
        view.askDeselectCoordinates(gameModelView);
    }
}

class PickTiles implements ViewEventHandler {

    @Override
    public void performAction(CLI view, GameModelView gameModelView) {
        if( !view.isMyTurn(gameModelView)) return;
        view.printAll(gameModelView);
        view.printSelectedTiles(gameModelView);
        if (gameModelView.getSelectedTiles().size() <= 1) view.selectColumn(gameModelView);
        else view.askTileOrder(gameModelView);
    }

}

class NewOrderSuccess implements ViewEventHandler {

    @Override
    public void performAction(CLI view, GameModelView gameModelView) {
        if( !view.isMyTurn(gameModelView)) return;
        view.printSelectedTiles(gameModelView);
        view.askTileOrder(gameModelView);
    }
}

class NewOrderFail implements ViewEventHandler {

    @Override
    public void performAction(CLI view, GameModelView gameModelView) {
        if( !view.isMyTurn(gameModelView)) return;
        System.out.println("The tile order is invalid.");
        view.askTileOrder(gameModelView);
    }
}

class SelectColumnFail implements ViewEventHandler {

    @Override
    public void performAction(CLI view, GameModelView gameModelView) {
        if( !view.isMyTurn(gameModelView)) return;
        System.out.println("The selected column is invalid.");
        view.selectColumn(gameModelView);
    }
}
