package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import it.polimi.ingsw.distributed.events.controllerEvents.MessageEvent;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import it.polimi.ingsw.util.Observable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static it.polimi.ingsw.distributed.events.ViewEvents.EventView.*;
import static it.polimi.ingsw.distributed.events.controllerEvents.EventController.*;

public class CLI extends Observable<MessageEvent> implements View, Runnable {
    private String thisUsername;
    private final HashMap<EventView, ViewEventHandler> viewEventHandlers;
    private final HashMap<Integer, String> commonGoalCardDescriptions;
    private boolean isMyTurn = false;
    private boolean chatAvailability = false;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // used for chat message input
    Thread chatThread;

    public CLI(){
        viewEventHandlers = new HashMap<>();
        commonGoalCardDescriptions = new HashMap<>();
        initEventHandlers();
        initCommonGoalCardDescription();
    }

    private void  initEventHandlers() {
        viewEventHandlers.put(SELECT_COORDINATES_SUCCESS, this::selectedCoordinatesSuccess);
        viewEventHandlers.put(SELECT_COORDINATES_FAIL,  this::selectedCoordinatesFail);
        viewEventHandlers.put(DESELECT_COORDINATES_SUCCESS, this::deselectCoordinatesSuccess);
        viewEventHandlers.put(DESELECT_COORDINATES_FAIL, this::deselectCoordinatesFail);
        viewEventHandlers.put(PICK_TILES_SUCCESS, this::pickTilesEvent);
        viewEventHandlers.put(NEW_ORDER_SUCCESS, this::newOrderSuccess);
        viewEventHandlers.put(NEW_ORDER_FAIL, this::newOrderFail);
        viewEventHandlers.put(SELECT_COLUMN_FAIL, this::selectColumnFail);
        viewEventHandlers.put(NEW_TURN, this::newTurn);
        viewEventHandlers.put(END_GAME, this::endGame);
        viewEventHandlers.put(UPDATE_CHAT, this::showChat);
    }

    private void initCommonGoalCardDescription() {
        commonGoalCardDescriptions.put(1, Const.desc1);
        commonGoalCardDescriptions.put(2, Const.desc2);
        commonGoalCardDescriptions.put(3, Const.desc3);
        commonGoalCardDescriptions.put(4, Const.desc4);
        commonGoalCardDescriptions.put(5, Const.desc5);
        commonGoalCardDescriptions.put(6, Const.desc6);
        commonGoalCardDescriptions.put(7, Const.desc7);
        commonGoalCardDescriptions.put(8, Const.desc8);
        commonGoalCardDescriptions.put(9, Const.desc9);
        commonGoalCardDescriptions.put(10, Const.desc10);
        commonGoalCardDescriptions.put(11, Const.desc11);
        commonGoalCardDescriptions.put(12, Const.desc12);
    }

    @Override
    public void run() {
    }

    @Override
    public void ViewEventHandler(GameModelView gameModelView, EventView eventView) {
        viewEventHandlers.get(eventView).performAction(gameModelView);
    }


    public void newTurn(GameModelView gameModelView){
        new Thread(() -> {
            printAll(gameModelView);
            //System.out.printf("It's %s's turn.\n", gameModelView.getCurrentPlayer());
            System.out.println("It's " + gameModelView.getCurrentPlayer() + "'s turn.");
            listenToPlayer(gameModelView);
        }).start();
    }


    private void listenToPlayer(GameModelView gameModelView) {
        try {
            // interrupt the prev chat thread on new turn
            if (chatThread != null && chatThread.isAlive()) {
                chatThread.interrupt();
                chatThread.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if ( isMyTurn(gameModelView)) {
            System.out.println("The Dotted spots on the board are the tiles that can be selected.");
            printCanBeSelectedCoordinates(gameModelView);
            String coordinates = getCoordinates();
            setChangedAndNotifyObservers(new MessageEvent(SELECT_COORDINATES, coordinates));
        } else {
            startChat();
        }
    }

    private void startChat() {
        chatThread = new Thread(() -> {
            System.out.println("Write your message and press enter to send it to the other players");
            System.out.println("write '/showChat' and press enter to get the last 10 messages from the chat");
            try {
                while (reader.ready()) {
                    reader.readLine(); // clear buffered reader
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String message = null;
                    if (reader.ready()) {
                        message = reader.readLine();
                        if (message.equals("/showChat")) {
                            setChangedAndNotifyObservers(new MessageEvent(SHOW_CHAT, thisUsername));
                        } else if (message.equals("/quit")) {
                            if (isMyTurn) {
                                return;
                            }
                            System.err.println("command not found");
                        } else if (!message.isBlank()) {
                            setChangedAndNotifyObservers(new MessageEvent(NEW_MESSAGE_CHAT, thisUsername + ": " + message));
                        }
                    }
                    Thread.sleep(100);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });

        chatThread.start();
    }

    public int getNumInput(){
        try {
            Scanner s = new Scanner(System.in) ;
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
            Scanner s = new Scanner(System.in);
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
            Scanner s = new Scanner(System.in) ;
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
            Scanner s = new Scanner(System.in);
            while (true) {
                System.out.println("Enter the coordinates: x y");
                input = s.nextLine();
                if (input.equals("/chat")) {
                    startChat();
                    if (chatThread.isAlive()) {
                        try {
                            chatThread.join();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    break;
                }
            }
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
        //System.out.println("Do you want to select a coordinate from the board? yes/no");
        String coordinates;
        if (askYesOrNo("Do you want to select a coordinate from the board? yes/no")){
            System.out.println("The Dotted spots on the board are the tiles that can be selected.");
            printCanBeSelectedCoordinates(gameModelView);
            coordinates = getCoordinates();
            setChangedAndNotifyObservers(new MessageEvent(SELECT_COORDINATES, coordinates));
        }
        else if(gameModelView.getSelectedCoordinates().size() == 0) {
            System.out.println("You have not selected a tile. Please select at least a tile.");
            System.out.println("The Dotted spots on the board are the tiles that can be selected.");
            printCanBeSelectedCoordinates(gameModelView);
            coordinates = getCoordinates();
            setChangedAndNotifyObservers(new MessageEvent(SELECT_COORDINATES, coordinates));
        }
        else pickTiles();
    }


    public void askDeselectCoordinates(GameModelView gameModelView) {
        printSelectedCoordinates(gameModelView);
        //System.out.println("Do you want to deselect coordinates? yes/no: ");
        if (askYesOrNo("Do you want to deselect coordinates? yes/no: ")) {
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
            Scanner s = new Scanner(System.in) ;
            answer = s.nextLine();
            if (!answer.equals("yes") && !answer.equals("no") && !answer.equals("y") && !answer.equals("n") ) System.out.println("Please select yes or no");
        } while( !answer.equals("yes") && !answer.equals("no")  && !answer.equals("y") && !answer.equals("n") );
        return answer.equals("yes") || answer.equals("y") ;
    }

    public boolean askYesOrNo(String text) {
        String answer;
        do {
            Scanner s = new Scanner(System.in) ;
            while (true) {
                System.out.println(text);
                answer = s.nextLine();
                if (answer.equals("/chat")) {
                    startChat();
                    if (chatThread.isAlive()) {
                        try {
                            chatThread.join();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    break;
                }
            }
            if (!answer.equals("yes") && !answer.equals("no") && !answer.equals("y") && !answer.equals("n") ) System.out.println("Please select yes or no");
        } while( !answer.equals("yes") && !answer.equals("no")  && !answer.equals("y") && !answer.equals("n") );
        return answer.equals("yes") || answer.equals("y") ;
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
        //System.out.println("Do you want to order your Tiles? yes/no");
        if (!askYesOrNo("Do you want to order your Tiles? yes/no")) selectColumn(gameModelView);
        else {
            String input = getTileOrder();
            setChangedAndNotifyObservers(new MessageEvent(NEW_ORDER, input));
        }
    }

    private String getTileOrder() {
        String input;
        boolean isValid;
        do {
            isValid = true;
            Scanner s = new Scanner(System.in) ;
            while (true) {
                System.out.println("Rearrange the tiles by setting a new array of indexes: ");
                System.out.println("(example of new order: 2 0 1)");
                input = s.nextLine();
                if (input.equals("/chat")) {
                    startChat();
                    if (chatThread.isAlive()) {
                        try {
                            chatThread.join();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    break;
                }
            }
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
        String input;
        String[] strArr;
        int x;
        do {
            x = -1;
            Scanner s = new Scanner(System.in) ;
            while (true) {
                System.out.printf("%s select the column where you want to put your tiles. ", gameModelView.getCurrentPlayer());
                System.out.println("Choose a number from 0 to 4: ");
                input = s.nextLine();
                if (input.equals("/chat")) {
                    startChat();
                    if (chatThread.isAlive()) {
                        try {
                            chatThread.join();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    break;
                }
            }
            strArr = input.split(" ");
            if ( !isNumeric(input) || strArr.length != 1) System.out.println("Invalid input. Try again: ");
            else {
                x = Integer.parseInt(input);
                if ( x < 0 || x > 4 ) System.out.println("Invalid input. Try Again: ");
            }
        } while (!isNumeric(input) || strArr.length != 1 || x < 0 || x > 4);
        setChangedAndNotifyObservers(new MessageEvent(SELECT_COLUMN, input));
    }


    private void setChangedAndNotifyObservers(MessageEvent arg) {
            setChanged();
            notifyObservers(arg);
    }

    public boolean isMyTurn(GameModelView gameModelView){
        isMyTurn = gameModelView.getCurrentPlayer().equals(thisUsername);
        return gameModelView.getCurrentPlayer().equals(thisUsername);
    }


    /***************************************************************************************************************/


    private void printLeaderboard(GameModelView gameModel) {
        int[] pointsList = gameModel.getPointsList();
        int[] sortedList = Arrays.copyOf(pointsList, pointsList.length);
        Arrays.sort(sortedList);
        for (int i = 0; i < pointsList.length; i++) {
            int element = pointsList[i];
            int position = Arrays.binarySearch(sortedList, element);
        }

        System.out.println("Leaderboard:");
        int i = 0;
        for (Integer position : pointsList) {
            System.out.println(gameModel.getPlayerList()[position] + ": " + pointsList[position]);
        }

    }



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

    public void printPersonalGoal(GameModelView gameModelView){
        ItemTileType[][] personalGoalCard = gameModelView.getPersonalGoalCardList().get(thisUsername);
        System.out.println("      Your personal Goal Card:");
        printMatrix(personalGoalCard);
    }


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
        border.append(String.format("%s (%d)%20s",gameModelView.getPlayerList()[0], gameModelView.getPointsList()[0], "" ));
        for (int i = 1; i < gameModelView.getBookshelfList().length; i++) {
            border.append(String.format("%s (%d)%20s",gameModelView.getPlayerList()[i], gameModelView.getPointsList()[i], ""));
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
        printCommonGoalCard(gameModelView);
        printBoard(gameModelView);
        printBookshelves(gameModelView);
        printPersonalGoal(gameModelView);
        System.out.println(" ");
    }

    private void printCommonGoalCard(GameModelView gameModelView) {
        HashMap<Integer, Integer[]> commonGoalCardDeck = gameModelView.getCommonGoalCardDeck();
        for (Map.Entry<Integer, Integer[]> set : commonGoalCardDeck.entrySet()) {
            System.out.print("      CommonGoalCard " + set.getKey() + ". Available points: ");
            for (Integer point : set.getValue()) {
                System.out.print(point + " ");
            }
            System.out.println(" ");
            String description = commonGoalCardDescriptions.get(set.getKey());
            System.out.println(description);
        }

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

    /******************************************************************************************/

    private void selectedCoordinatesFail(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        printAll(gameModelView);
        System.out.println("The selected coordinates are not available");
        if(gameModelView.getSelectedCoordinates().size() == 0) {
            System.out.println("You have not selected a tile. Please select at least a tile.");
            System.out.println("The Dotted spots on the board are the tiles that can be selected.");
            printCanBeSelectedCoordinates(gameModelView);
            String coordinates = getCoordinates();
            setChangedAndNotifyObservers(new MessageEvent(SELECT_COORDINATES, coordinates));
        }
        else selectCoordinates(gameModelView);
    }

    private void selectedCoordinatesSuccess(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        printAll(gameModelView);
        askDeselectCoordinates(gameModelView);
    }

    private void deselectCoordinatesSuccess(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        printAll(gameModelView);
        selectCoordinates(gameModelView);
    }

    private void deselectCoordinatesFail( GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        printAll(gameModelView);
        askDeselectCoordinates(gameModelView);
    }

    private void pickTilesEvent(GameModelView gameModelView ) {
        if( !isMyTurn(gameModelView)) return;
        printAll(gameModelView);
        printSelectedTiles(gameModelView);
        if (gameModelView.getSelectedTiles().size() <= 1) selectColumn(gameModelView);
        else askTileOrder(gameModelView);
    }

    private void newOrderSuccess(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        printSelectedTiles(gameModelView);
        askTileOrder(gameModelView);
    }

    private void newOrderFail(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        System.out.println("The tile order is invalid.");
        askTileOrder(gameModelView);
    }

    private void selectColumnFail(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        System.out.println("The selected column is invalid.");
        selectColumn(gameModelView);
    }

    private void endGame(GameModelView gameModel) {
        printBookshelves(gameModel);
        System.out.println(" ");
        System.out.println("The Game has ended.");
        printLeaderboard(gameModel);
    }

    private void showLastMessages(GameModelView gameModelView) {
        for (String message : gameModelView.getChat()) {
            System.out.println(message);
        }
    }
    private void showChat(GameModelView gameModelView) {
        if (isMyTurn(gameModelView) && !chatAvailability) {
            return;
        }

        String[] chat = gameModelView.getChat().toArray(new String[10]);

        int index = 0;
        for (int i = 0; i < 10; i++) {
            if (chat[i] == null) {
                break;
            }
            index = i;
        }

        System.out.println(chat[index]);
    }

}

@FunctionalInterface
interface ViewEventHandler {
    void performAction(GameModelView gameModelView);
}
