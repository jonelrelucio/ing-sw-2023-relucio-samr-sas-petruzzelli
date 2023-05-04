package it.polimi.ingsw.server.model;

import java.util.ArrayList;
import java.util.Arrays;

import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import it.polimi.ingsw.server.model.commonGoalCard.CommonGoalCard;
import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.util.Observable;

enum PlayerState {
    WAITING, PLAYING;
}

public class Player extends Observable<GameEvent> {

    private static final int ENDTOKENSCORE = 1;
    private boolean winner;

    private final String nickname;
    private int score;
    private final Board board;
    private Bookshelf bookshelf;
    private PersonalGoalCard personalGoalCard;
    private int numOfRounds;
    private PlayerState playerState;
    private ArrayList<CommonGoalCard> obtainedCommonGoalCards;
    private int obtainedCommonGoalPoints;
    private ArrayList<ItemTile> selectedItemTiles;



    public Player(String nickname, PersonalGoalCard personalGoalCard, Board board){
        this.nickname = nickname;
        this.personalGoalCard = personalGoalCard;
        this.bookshelf = new Bookshelf();
        this.board = board;
        selectedItemTiles = new ArrayList<>();
        obtainedCommonGoalCards = new ArrayList<>();
    }

    // Getters
    public String getNickname() { return nickname; }
    public int getScore() { return score; }
    public Bookshelf getBookshelf() { return bookshelf; }
    public PersonalGoalCard getPersonalGoalCard() { return personalGoalCard; }
    public ArrayList<ItemTile> getSelectedItemTiles() { return selectedItemTiles; }
    public int getNumOfRounds() { return numOfRounds; }
    public PlayerState getPlayerState() { return playerState; }
    public ArrayList<CommonGoalCard> getObtainedCommonGoalCards() { return obtainedCommonGoalCards;}
    public int getObtainedCommonGoalPoints() { return obtainedCommonGoalPoints; }
    public Board getBoard() {return board;}

    // Setters
    public void setScore(int score) {
        this.score = score;
    }
    public void setBookshelf(Bookshelf bookshelf) {this.bookshelf = bookshelf;}
    public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {this.personalGoalCard = personalGoalCard;}
    public void setNumOfRounds(int numOfRounds) {this.numOfRounds = numOfRounds;}
    public void setPlayerState(PlayerState playerState) {this.playerState = playerState;}
    public void setObtainedCommonGoalCards(CommonGoalCard card) { this.obtainedCommonGoalCards.add(card);}
    public void setObtainedCommonGoalPoints(int obtainedCommonGoalPoints) { this.obtainedCommonGoalPoints = obtainedCommonGoalPoints; }


    public boolean isWinner() { return winner; }
    public void setWinner() { this.winner = true; }
    public int getEndGameToken() {
        if(!isWinner()) throw new IllegalCallerException("Can't call EndGameToken if player is not the winner");
        return ENDTOKENSCORE;
    }


    /**
     * Adds the given coordinates in the ArrayList of selectedTiles and updates the canBeSelectedTiles Arraylist
     * @param coordinates   selected coordinates
     */
    public void selectCoordinates(int[] coordinates) {
        if (board.getSelectedCoordinates().size() > bookshelf.getMaxAvailableSpace())
            throw new IllegalArgumentException("Can't select more tiles.");
        for (int[] tile : board.getCanBeSelectedCoordinates()) {
            if (Arrays.equals(tile, coordinates)) {
                board.getSelectedCoordinates().add(coordinates);
                board.updateCanBeSelectedCoordinates();
                return;
            }
        }
        throw new RuntimeException("Item in given coordinates can't be selected");
    }

    /**
     * Pops the given coordinates from the Arraylist of selectedTiles and updates the canBeSelectedTiles ArrayList
     * @param coordinates   coordinates to be popped from the ArrayList of selectedTiles
     */
    public void deselectCoordinates(int[] coordinates ){
        for (int i = 0; i < board.getSelectedCoordinates().size(); i++){
            if (coordinates[0] == board.getSelectedCoordinates().get(i)[0] && coordinates[1] == board.getSelectedCoordinates().get(i)[1]) {
                board.getSelectedCoordinates().remove(i);
                board.updateCanBeSelectedCoordinates();
                return;
            }
        }
        throw new RuntimeException("No coordinates in selectedTiles,");

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
     * @param newOrder  the order of the selectedItemTiles to be rearranged
     */
    public void rearrangeSelectedItemTiles(int... newOrder) {
        ArrayList<ItemTile> rearrangedItems = new ArrayList<ItemTile>();

        // Check that the new order array is valid
        if (newOrder.length != selectedItemTiles.size() || newOrder.length == 0) {
            throw new IllegalArgumentException("Invalid new order array");
        }

        // Add the items to the new list in the specified order
        for (int index : newOrder) {
            if (index < 0 || index >= selectedItemTiles.size()) {
                throw new IllegalArgumentException("Invalid index in new order array");
            }
            rearrangedItems.add(selectedItemTiles.get(index));
        }

        selectedItemTiles = rearrangedItems;
    }

    private void updateAndNotifyObservers(GameEvent arg) {
        setChanged();
        notifyObservers(arg);
    }



}
