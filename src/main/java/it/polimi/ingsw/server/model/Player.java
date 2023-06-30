package it.polimi.ingsw.server.model;

import java.util.ArrayList;
import java.util.Arrays;

import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import it.polimi.ingsw.server.model.commonGoalCard.CommonGoalCard;
import it.polimi.ingsw.server.model.ItemTile.ItemTile;

import static it.polimi.ingsw.distributed.events.ViewEvents.EventView.*;

/**
 * The state of the player
 */
enum PlayerState {
    WAITING, PLAYING;
}

/**
 * The player class
 */
public class Player {

    /**
     * The end token score which is the added to the final score of the player at the end of the match
     */
    private static final int ENDTOKENSCORE = 1;

    /**
     * Boolean value that indicates if the player is the winner or not
     */
    private boolean winner;

    /**
     * The nickname of the player
     */
    private final String nickname;

    /**
     * The score of the player
     */
    private int score;

    /**
     * a reference to the board that the player is playing at
     */
    private final Board board;

    /**
     * The bookshelf of the player
     */
    private Bookshelf bookshelf;

    /**
     * The personal goal card of the player
     */
    private PersonalGoalCard personalGoalCard;

    /**
     * The number of rounds of the player
     */
    private int numOfRounds;

    /**
     * The state of the player
     */
    private PlayerState playerState;

    /**
     * The obtained common goal cards
     */
    private ArrayList<CommonGoalCard> obtainedCommonGoalCards;

    /**
     * the obtained common goal points
     */
    private int obtainedCommonGoalPoints;

    /**
     * The selected item tiles
     */
    private ArrayList<ItemTile> selectedItemTiles;

    /**
     * boolean that checks if tits the first to have filled the bookshelf
     */
    private boolean firstToFillBookshelf;


    /**
     * Constructor
     * @param nickname          nickname of the player
     * @param personalGoalCard  personal goal card of the player
     * @param board             the reference to the board
     */
    public Player(String nickname, PersonalGoalCard personalGoalCard, Board board){
        this.nickname = nickname;
        this.personalGoalCard = personalGoalCard;
        this.bookshelf = new Bookshelf();
        this.board = board;
        selectedItemTiles = new ArrayList<>();
        obtainedCommonGoalCards = new ArrayList<>();
    }

    /**
     * Gets the nickname
     * @return  the nickname of the player
     */
    public String getNickname() { return nickname; }

    /**
     * Gets the score
     * @return  the score of the player
     */
    public int getScore() { return score; }

    /**
     * Gets the bookshelf
     * @return  the bookshelf
     */
    public Bookshelf getBookshelf() { return bookshelf; }

    /**
     * Gets the personal goal card
     * @return the personal goal card
     */
    public PersonalGoalCard getPersonalGoalCard() { return personalGoalCard; }

    /**
     * Gets the selected item tiles
     * @return  the selected item tiles
     */
    public ArrayList<ItemTile> getSelectedItemTiles() { return selectedItemTiles; }

    /**
     * Gets the number of rounds
     * @return  the number of rounds
     */
    public int getNumOfRounds() { return numOfRounds; }

    /**
     * Gets the state of the player
     * @return  the player state
     */
    public PlayerState getPlayerState() { return playerState; }

    /**
     * Gets the obtained common goal cards
     * @return the obtained common goal cards
     */
    public ArrayList<CommonGoalCard> getObtainedCommonGoalCards() { return obtainedCommonGoalCards;}

    /**
     * Gets the obtained common goal points
     * @return the obtained common goal points
     */
    public int getObtainedCommonGoalPoints() { return obtainedCommonGoalPoints; }

    /**
     * gets the board
     * @return  the board
     */
    public Board getBoard() {return board;}

    /**
     * sets the score
     * @param score the score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Sets the bookshelf
     * @param bookshelf the bookshelf
     */
    public void setBookshelf(Bookshelf bookshelf) {this.bookshelf = bookshelf;}

    /**
     * Sets the personal goal card
     * @param personalGoalCard  the personal goal card
     */
    public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {this.personalGoalCard = personalGoalCard;}

    /**
     * Sets the number of rounds
     * @param numOfRounds
     */
    public void setNumOfRounds(int numOfRounds) {this.numOfRounds = numOfRounds;}

    /**
     * Sets the player state
     * @param playerState   the player state
     */
    public void setPlayerState(PlayerState playerState) {this.playerState = playerState;}

    /**
     * Sets the obtained common goal cards
     * @param card  the common goal card
     */
    public void setObtainedCommonGoalCards(CommonGoalCard card) { this.obtainedCommonGoalCards.add(card);}

    /**
     * Sets the obtained common goal points
     * @param obtainedCommonGoalPoints  the obtained common goal points
     */
    public void setObtainedCommonGoalPoints(int obtainedCommonGoalPoints) { this.obtainedCommonGoalPoints = obtainedCommonGoalPoints; }

    /**
     * Checks if player is winner
     * @return  true if winner
     */
    public boolean isWinner() { return winner; }

    /**
     * Sets the player attribute winner to true
     */
    public void setWinner() { this.winner = true; }

    /**
     * Gets the end game token
     * @return  the end game token
     */
    public int getEndGameToken() { return ENDTOKENSCORE; }


    /**
     * Adds the given coordinates in the ArrayList of selectedTiles and updates the canBeSelectedTiles Arraylist
     * @param coordinates   selected coordinates
     */
    public EventView selectCoordinates(int[] coordinates) {
        if (board.getSelectedCoordinates().size() + 1 > bookshelf.getMaxAvailableSpace())
            return SELECT_COORDINATES_FAIL;
        for (int[] tile : board.getCanBeSelectedCoordinates()) {
            if (Arrays.equals(tile, coordinates)) {
                board.getSelectedCoordinates().add(coordinates);
                board.updateCanBeSelectedCoordinates();
                System.out.println(nickname + " selected coordinates from the board.");
                return SELECT_COORDINATES_SUCCESS;
            }

        }
        System.out.println("Select coordinates fail");
        return SELECT_COORDINATES_FAIL;
    }

    /**
     * Pops the given coordinates from the Arraylist of selectedTiles and updates the canBeSelectedTiles ArrayList
     * @param coordinates   coordinates to be popped from the ArrayList of selectedTiles
     */
    public EventView deselectCoordinates(int[] coordinates ){
        for (int i = 0; i < board.getSelectedCoordinates().size(); i++){
            if (coordinates[0] == board.getSelectedCoordinates().get(i)[0] && coordinates[1] == board.getSelectedCoordinates().get(i)[1]) {
                board.getSelectedCoordinates().remove(i);
                board.updateCanBeSelectedCoordinates();
                System.out.println(nickname + " deselected coordinates from the board.");
                return DESELECT_COORDINATES_SUCCESS;
            }
        }


        System.out.println("Deselect coordinates fail");
        return DESELECT_COORDINATES_FAIL;
    }

    /**
     * gets the ItemTiles from the coordinates in the arraylist selectedCoordinates
     */
    public void pickSelectedItemTiles() {
        for (int[] indices : board.getSelectedCoordinates()) {
            selectedItemTiles.add(board.getBoardMatrix()[indices[0]][indices[1]]);
            board.getBoardMatrix()[indices[0]][indices[1]] = new ItemTile(ItemTileType.EMPTY);
        }
        board.getSelectedCoordinates().clear();
        board.updateCanBeSelectedCoordinates();
    }


    /**
     * rearranges items in selectedItemTiles
     *
     * @param newOrder the order of the selectedItemTiles to be rearranged
     * @return  success or fail event to clients.
     */
    public EventView rearrangeSelectedItemTiles(int... newOrder) {
        ArrayList<ItemTile> rearrangedItems = new ArrayList<ItemTile>();

        if (newOrder.length != selectedItemTiles.size() || newOrder.length == 0) {
            return NEW_ORDER_FAIL;
        }

        for (int index : newOrder) {
            if (index < 0 || index >= selectedItemTiles.size()) {
                return NEW_ORDER_FAIL;
            }
            rearrangedItems.add(selectedItemTiles.get(index));
        }

        selectedItemTiles = rearrangedItems;
        return NEW_ORDER_SUCCESS;
    }

    /**
     * Puts the item in the selected column
     * @param selectedColumn    the selected column
     * @return  the event to send to the client
     */
    public EventView putItemsInSelectedColumn(int selectedColumn){
        bookshelf.selectColumn(selectedColumn);
        return bookshelf.updateTiles(selectedItemTiles);
    }

    /**
     * Checks if player is first to fill the bookshelf
     * @return  true if first to fill the bookshelf
     */
    public boolean isFirstToFillBookshelf() {
        return firstToFillBookshelf;
    }

    /**
     * Sets the players firest to fill the bookshelf to true
     */
    public void setFirstToFillBookshelf() {
        this.firstToFillBookshelf = true;
    }
}
