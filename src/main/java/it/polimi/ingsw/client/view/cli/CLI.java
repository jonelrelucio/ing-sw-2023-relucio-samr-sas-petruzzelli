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

/**
 * This class manage all the input and all the different stages of the game using a Command Line Interface (CLI)
 */
public class CLI extends Observable<MessageEvent> implements View, Runnable {
    /**
     * Player's username
     */
    private String thisUsername;
    /**
     * HashMap that contain events as keys and #VireEventHandler as value, its purpose is to handle all the events triggered by the server
     */
    private final HashMap<EventView, ViewEventHandler> viewEventHandlers;
    /**
     * Associate each common goal card with its description
     */
    private final HashMap<Integer, String> commonGoalCardDescriptions;
    /**
     * Flag that indicate if it's the player's turn
     */
    private boolean isMyTurn = false;
    /**
     * Flag used to specify if the player can join the chat
     */
    private boolean chatAvailability = false;
    /**
     * Reader used for the chat messages
     */
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // used for chat message input
    /**
     * Chat thread
     */
    private Thread chatThread;

    /**
     * Initialize the CLI and call:
     * @see #initEventHandlers()
     * @see #initCommonGoalCardDescription()
     */
    public CLI(){
        viewEventHandlers = new HashMap<>();
        commonGoalCardDescriptions = new HashMap<>();
        initEventHandlers();
        initCommonGoalCardDescription();
    }

    /**
     * Put all the expected events called by the server as keys of 'viewEventHandlers' HashMap and the methods used to manage the events as values
     */
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
        viewEventHandlers.put(SHOW_LAST_MESSAGES, this::printChat);
    }

    /**
     * Fills the 'commonGoalCardDescriptions' HashMap with all the cards id and their description
     */
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

    /**
     * Override of the run() method inherited by Runnable interface
     */
    @Override
    public void run() {
    }

    /**
     * Execute the action related to the calling event
     * @param gameModelView
     * @param eventView
     */
    @Override
    public void ViewEventHandler(GameModelView gameModelView, EventView eventView) {
        viewEventHandlers.get(eventView).performAction(gameModelView);
    }

    /**
     * Create a new thread that manage the game turn
     * @param gameModelView
     * @see #printAll(GameModelView)
     * @see #listenToPlayer(GameModelView)
     */
    public void newTurn(GameModelView gameModelView){
        new Thread(() -> {
            printAll(gameModelView);
            System.out.println("It's " + gameModelView.getCurrentPlayer() + "'s turn.");
            listenToPlayer(gameModelView);
        }).start();
    }

    /**
     * If it's the player's turn starts the sequence of action that the player has must perform, otherwise starts the chat thread
     * @param gameModelView
     * @see #printCanBeSelectedCoordinates(GameModelView)
     * @see #getCoordinates()
     * @see #setChangedAndNotifyObservers(MessageEvent)
     * @see #startChat()
     */
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

    /**
     * Start the chat thread:
     * create a new thread that manage the message exchange between the players.
     * The client notify the server in order to retrieve the last 10 messages in the chat.
     * While the thread is active it checks if the current player is typing '/quit', if so the thread stops.
     * @throws RuntimeException
     * @see #setChangedAndNotifyObservers(MessageEvent)
     */
    private void startChat() {
        chatThread = new Thread(() -> {
            System.out.println("Write your message and press enter to send it to the other players");

            setChangedAndNotifyObservers(new MessageEvent(SHOW_CHAT, thisUsername));

            try {
                while (reader.ready()) {
                    reader.readLine(); // clear buffered reader
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String message;
                    if (reader.ready()) {
                        message = reader.readLine();
                        if (message.equals("/quit")) {
                            if (isMyTurn) {
                                chatAvailability = false;
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

    /**
     * Read the user input and check its validity.
     * @return the number inserted by the user as int or recall getNumInput() if was not inserted a number
     */
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

    /**
     * Read the user input and check its validity.
     * @return the username chosen by the player as a String
     */
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


    /**
     * Read the user input and check its validity.
     * @return the max number of player for the current game
     * @see #getNumInput()
     */
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

    /**
     * Print the String parameter.
     * @param string Message to be printed
     */
    @Override
    public void printMessage(String string) {
        System.out.println(string);
    }

    /**
     * Set the field 'thisUsername'
     * @param thisUsername The username chosen by the player
     */
    @Override
    public void setThisUsername(String thisUsername) {
        this.thisUsername = thisUsername;
    }

    /**
     * Read the user input and check if it's valid.
     * If the user types '/chat' starts the chat thread, otherwise split the input into two coordinates and check if are valid
     * @return the coordinates inserted by the user as a String
     * @see #startChat()
     * @see #isNumeric(String)
     */
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

    /**
     * Ask the player to choose if wants to select a coordinate or not and notify the server with the selected coordinates
     * @param gameModelView
     * @see #askYesOrNo(String)
     * @see #printCanBeSelectedCoordinates(GameModelView)
     * @see #getCoordinates()
     * @see #setChangedAndNotifyObservers(MessageEvent)
     */
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

    /**
     * Ask the player to choose if wants to deselect a coordinate or not
     * @param gameModelView
     * @see #printSelectedCoordinates(GameModelView)
     * @see #askYesOrNo(String)
     * @see #getCoordinates()
     * @see #setChangedAndNotifyObservers(MessageEvent)
     * @see #selectCoordinates(GameModelView)
     */
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

    /**
     * Notify the server which item tiles pick from the board
     * @see #setChangedAndNotifyObservers(MessageEvent)
     */
    private void pickTiles() {
        setChangedAndNotifyObservers(new MessageEvent(PICK_TILES, " "));
    }

    /**
     * Read the user input and check if it's valid
     * @return true if the user answer 'yes', false if the user answer 'no'
     */
    public boolean askYesOrNo() {
        String answer;
        do {
            Scanner s = new Scanner(System.in) ;
            answer = s.nextLine();
            if (!answer.equals("yes") && !answer.equals("no") && !answer.equals("y") && !answer.equals("n") ) System.out.println("Please select yes or no");
        } while( !answer.equals("yes") && !answer.equals("no")  && !answer.equals("y") && !answer.equals("n") );
        return answer.equals("yes") || answer.equals("y") ;
    }

    /**
     * Print the question contained into the 'text' parameter and read the user's answer. If the user types '/chat' starts the chat thread
     * @param text
     * @return true if the user answer 'yes', false if the user answer 'no'
     * @see #startChat()
     */
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

    /**
     * Check if the parameter 'str' is a number
     * @param str
     * @return true if 'str' is a number, else false
     */
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Ask the player if wants to order the selected tiles,
     * if yes allow the player to select the new order and notify the server, otherwise ask the player to select a column
     * @param gameModelView
     * @see #askYesOrNo(String)
     * @see #selectColumn(GameModelView)
     * @see #getTileOrder()
     * @see #setChangedAndNotifyObservers(MessageEvent)
     */
    public void askTileOrder(GameModelView gameModelView) {
        //System.out.println("Do you want to order your Tiles? yes/no");
        if (!askYesOrNo("Do you want to order your Tiles? yes/no")) selectColumn(gameModelView);
        else {
            String input = getTileOrder();
            setChangedAndNotifyObservers(new MessageEvent(NEW_ORDER, input));
        }
    }

    /**
     * Read the user's input and check if it's valid. If the user types '/chat' starts the chat thread.
     * @return the new order of the selected tiles
     * @see #startChat()
     * @see #isNumeric(String)
     */
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

    /**
     * Ask the player to select a column to fill with the selected tiles.
     * If the user types '/chat' starts the chat thread.
     * Read the user's input and check it's validity, if is valid notify the server.
     * @param gameModelView
     * @see #printBookshelves(GameModelView)
     * @see #startChat()
     * @see #isNumeric(String)
     * @see #setChangedAndNotifyObservers(MessageEvent)
     */
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

    /**
     * call setChanged() and notify all the observers with the event contained into the 'arg' parameter
     * @param arg
     * @see #setChanged()
     * @see #notifyObservers(MessageEvent)
     */
    private void setChangedAndNotifyObservers(MessageEvent arg) {
        setChanged();
        notifyObservers(arg);
    }

    /**
     * Check if it's the player's turn
     * @param gameModelView
     * @return true if it is, otherwise false
     */
    public boolean isMyTurn(GameModelView gameModelView){
        isMyTurn = gameModelView.getCurrentPlayer().equals(thisUsername);
        return gameModelView.getCurrentPlayer().equals(thisUsername);
    }


    /***************************************************************************************************************/

    /**
     * Print the end game screen
     * @param gameModel
     */
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

    /**
     * Print the selected tiles
     * @param gameModelView
     */
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

    /**
     * Check if the 'list' parameter contains the coordinates of {i, j}
     * @param list
     * @param i
     * @param j
     * @return true if yes, otherwise false
     */
    private boolean containsCoordinates(ArrayList<int[]> list, int i, int j){
        int[] coordinates = new int[]{i, j};
        for (int[] array : list) {
            if (Arrays.equals(array, coordinates)) return true;
        }
        return false;
    }

    /**
     * Print the board game
     * @param gameModelView
     * @see #containsCoordinates(ArrayList, int, int)
     */
    public void printBoard(GameModelView gameModelView) {
        int n = 1;
        System.out.println("      Game Board with "+ gameModelView.getPlayerList().length + " players");
        System.out.println("             1   2   3   4   5   6   7   8   9");
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
            System.out.println("│  " + n);
            n++;
            System.out.println("      │                                            │");
        }
        System.out.println("      └────────────────────────────────────────────┘");
    }

    /**
     * Print the 'matrix' parameter
     * @param matrix
     */
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

    /**
     * Print the personal goal card
     * @param gameModelView
     * @see #printMatrix(ItemTileType[][])
     */
    public void printPersonalGoal(GameModelView gameModelView){
        ItemTileType[][] personalGoalCard = gameModelView.getPersonalGoalCardList().get(thisUsername);
        System.out.println("      Your personal Goal Card:");
        printMatrix(personalGoalCard);
    }

    /**
     * Print the bookshelves of all the players
     * @param gameModelView
     * @see #getPlayerNickname(GameModelView)
     * @see #getTopBorder(int)
     * @see #getMidBorder(int)
     * @see #getBotBorder(int)
     */
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

    /**
     * Build the header of the bookshelves with the players name
     * @param gameModelView
     * @return the header as a String
     */
    private String getPlayerNickname(GameModelView gameModelView) {
        StringBuilder border = new StringBuilder();
        border.append(String.format("%s (%d)%20s",gameModelView.getPlayerList()[0], gameModelView.getPointsList()[0], "" ));
        for (int i = 1; i < gameModelView.getBookshelfList().length; i++) {
            border.append(String.format("%s (%d)%20s",gameModelView.getPlayerList()[i], gameModelView.getPointsList()[i], ""));
        }
        return border.toString();
    }

    /**
     * Build the top border of the bookshelf
     * @param numMatrices
     * @return the top border as a String
     */
    private String getTopBorder(int numMatrices) {
        StringBuilder border = new StringBuilder();
        border.append("┌───┬───┬───┬───┬───┐");
        for (int i = 1; i < numMatrices; i++) {
            border.append("       ┌───┬───┬───┬───┬───┐");
        }
        return border.toString();
    }

    /**
     * Build the middle border of the bookshelf
     * @param numMatrices
     * @return the middle border as a String
     */
    private String getMidBorder(int numMatrices) {
        StringBuilder border = new StringBuilder();
        border.append("├───┼───┼───┼───┼───┤");
        for (int i = 1; i < numMatrices; i++) {
            border.append("       ├───┼───┼───┼───┼───┤");
        }
        return border.toString();
    }

    /**
     * Build the bottom border of the bookshelf
     * @param numMatrices
     * @return the bottom border as a String
     */
    private String getBotBorder(int numMatrices) {
        StringBuilder border = new StringBuilder();
        border.append("└───┴───┴───┴───┴───┘");
        for (int i = 1; i < numMatrices; i++) {
            border.append("       └───┴───┴───┴───┴───┘");
        }
        return border.toString();
    }

    /**
     * Print all the element of the game: deck of two common goal card, board, bookshelves and personal goal card
     * @param gameModelView
     * @see #printCommonGoalCard(GameModelView)
     * @see #printBoard(GameModelView)
     * @see #printBookshelves(GameModelView)
     * @see #printPersonalGoal(GameModelView)
     */
    public void printAll(GameModelView gameModelView){
        System.out.println(" ");
        System.out.println(" ");
        printCommonGoalCard(gameModelView);
        printBoard(gameModelView);
        printBookshelves(gameModelView);
        printPersonalGoal(gameModelView);
        System.out.println(" ");
    }

    /**
     * Print the common goal cards and their description
     * @param gameModelView
     */
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

    /**
     * Print which tiles can be selected based on the game rules
     * @param gameModelView
     */
    public void printCanBeSelectedCoordinates(GameModelView gameModelView) {
        System.out.println("Can be selected Tiles coordinates: ");
        for ( int[] coordinates : gameModelView.getCanBeSelectedCoordinates() ){
            System.out.printf(" [%d %d]", coordinates[0], coordinates[1]);
        }
        System.out.println(" ");
    }

    /**
     * Print the selected coordinates
     * @param gameModelView
     */
    public void printSelectedCoordinates(GameModelView gameModelView) {
        if (gameModelView.getSelectedCoordinates().isEmpty()) return;
        System.out.println("These are the coordinates that you have selected: ");
        for ( int[] coordinates : gameModelView.getSelectedCoordinates() ){
            System.out.printf(" [%d %d]", coordinates[0], coordinates[1]);
        }
        System.out.println(" ");
    }

    /******************************************************************************************/

    /**
     * The method is called when an error occurs during the coordinates selection step.
     * If it's the player's turn ask again to select the coordinates and notify the server.
     * @param gameModelView
     * @see #isMyTurn(GameModelView)
     * @see #printAll(GameModelView)
     * @see #printCanBeSelectedCoordinates(GameModelView)
     * @see #getCoordinates()
     * @see #setChangedAndNotifyObservers(MessageEvent)
     * @see #selectCoordinates(GameModelView)
     */
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

    /**
     * If the coordinates selection action goes well ask if the player wants to deselect some coordinates
     * @param gameModelView
     * @see #isMyTurn(GameModelView)
     * @see #printAll(GameModelView)
     * @see #askDeselectCoordinates(GameModelView)
     */
    private void selectedCoordinatesSuccess(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        printAll(gameModelView);
        askDeselectCoordinates(gameModelView);
    }

    /**
     * If the coordinates deselection action goes well ask if the player wants to select some coordinates
     * @param gameModelView
     * @see #isMyTurn(GameModelView)
     * @see #printAll(GameModelView)
     * @see #selectCoordinates(GameModelView)
     */
    private void deselectCoordinatesSuccess(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        printAll(gameModelView);
        selectCoordinates(gameModelView);
    }

    /**
     * The method is called when an error occurs during the coordinates deselection step.
     * If it's the player's turn ask again to deselect the coordinates.
     * @param gameModelView
     * @see #isMyTurn(GameModelView)
     * @see #printAll(GameModelView)
     * @see #askDeselectCoordinates(GameModelView)
     */
    private void deselectCoordinatesFail( GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        printAll(gameModelView);
        askDeselectCoordinates(gameModelView);
    }

    /**
     * The method is called if the action of pick the tiles from the board goes well
     * @param gameModelView
     * @see #isMyTurn(GameModelView)
     * @see #printAll(GameModelView)
     * @see #printSelectedTiles(GameModelView)
     * @see #selectColumn(GameModelView)
     * @see #askTileOrder(GameModelView)
     */
    private void pickTilesEvent(GameModelView gameModelView ) {
        if( !isMyTurn(gameModelView)) return;
        printAll(gameModelView);
        printSelectedTiles(gameModelView);
        if (gameModelView.getSelectedTiles().size() <= 1) selectColumn(gameModelView);
        else askTileOrder(gameModelView);
    }

    /**
     * If the action of set the order of the selected tiles goes well print the selected tiles
     * and ask again if the player wants to set a new order
     * @param gameModelView
     * @see #isMyTurn(GameModelView)
     * @see #printSelectedTiles(GameModelView)
     * @see #askTileOrder(GameModelView)
     */
    private void newOrderSuccess(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        printSelectedTiles(gameModelView);
        askTileOrder(gameModelView);
    }

    /**
     * If the action of set the order of the selected tiles goes wrong ask again if the player wants to set a new order
     * @param gameModelView
     * @see #isMyTurn(GameModelView)
     * @see #askTileOrder(GameModelView)
     */
    private void newOrderFail(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        System.out.println("The tile order is invalid.");
        askTileOrder(gameModelView);
    }

    /**
     * If the action of select a column goes wrong ask again to select a column
     * @param gameModelView
     * @see #isMyTurn(GameModelView)
     * @see #selectColumn(GameModelView)
     */
    private void selectColumnFail(GameModelView gameModelView) {
        if( !isMyTurn(gameModelView)) return;
        System.out.println("The selected column is invalid.");
        selectColumn(gameModelView);
    }

    /**
     * Print the bookshelves and the end game screen
     * @param gameModel
     * @see #printBookshelves(GameModelView)
     * @see #printLeaderboard(GameModelView)
     */
    private void endGame(GameModelView gameModel) {
        printBookshelves(gameModel);
        System.out.println(" ");
        System.out.println("The Game has ended.");
        printLeaderboard(gameModel);
    }

    /**
     * Print the last message in the chat
     * @param gameModelView
     */
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

        String[] message = chat[index].split(":");

        String color = "\033[0;33m";

        if (message[0].equals(thisUsername)) {
            color = "\033[0;34m";
        }

        System.out.println(color + message[0] + ":" + "\033[0m"  + message[1]);
    }

    /**
     * This method retrieve the last 10 messages in the chat and print them
     * @param gameModelView
     */
    public void printChat(GameModelView gameModelView) {
        for (String m : gameModelView.getChat()) {
            String[] message = m.split(":");

            String color = "\033[0;33m";
            if (message[0].equals(thisUsername)) {
                color = "\033[0;34m";
            }

            System.out.println(color + message[0] + ":" + "\033[0m"  + message[1]);
        }
        if (isMyTurn) {
            chatAvailability = true;
        }
    }

}

/**
 * Declares the 'performAction()' method
 */
@FunctionalInterface
interface ViewEventHandler {
    /**
     * Declaration of the method 'performAction()'
     * @param gameModelView
     */
    void performAction(GameModelView gameModelView);
}