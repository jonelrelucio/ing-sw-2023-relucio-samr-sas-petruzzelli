package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;

import it.polimi.ingsw.model.ItemTile.ItemTileType;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.ItemTile.ItemTile;

enum PlayerState {
    WAITING, PLAYING;
    @Override
    public String toString() {
        return super.toString();
    }
}

public class Player {

    private static final int ENDTOKENSCORE = 1;
    private boolean winner;

    private final String nickname;
    private int score;
    private final Board board;
    private final Bookshelf bookshelf;
    private PersonalGoalCard personalGoalCard;
    private ArrayList<ItemTile> selectedTiles;
    private int numOfRounds;
    private PlayerState playerState;
    private ArrayList<CommonGoalCard> obtainedCommonGoalCards;
    private int obtainedCommonGoalPoints;



    public Player(String nickname, PersonalGoalCard personalGoalCard, Board board){
        this.nickname = nickname;
        this.personalGoalCard = personalGoalCard;
        this.bookshelf = new Bookshelf();
        this.board = board;
        selectedTiles = new ArrayList<>();
        obtainedCommonGoalCards = new ArrayList<>();
    }

    // Getters
    public String getNickname() { return nickname; }
    public int getScore() { return score; }
    public Bookshelf getBookshelf() { return bookshelf; }
    public PersonalGoalCard getPersonalGoalCard() { return personalGoalCard; }
    public ArrayList<ItemTile> getSelectedTiles() { return selectedTiles; }
    public int getNumOfRounds() { return numOfRounds; }
    public PlayerState getPlayerState() { return playerState; }
    public ArrayList<CommonGoalCard> getObtainedCommonGoalCards() { return obtainedCommonGoalCards;}
    public int getObtainedCommonGoalPoints() { return obtainedCommonGoalPoints; }

    // Setters
    public void setScore(int score) {this.score = score;}
    public void setBookshelf(Bookshelf bookshelf) {this.bookshelf = bookshelf;}
    public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {this.personalGoalCard = personalGoalCard;}
    public void setSelectedTiles(ArrayList<ItemTile> selectedTiles) {this.selectedTiles = selectedTiles;}
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
    public void selectTile(int[] coordinates, Bookshelf bookshelf, Board board) {
        if (board.getCanBeSelectedCoordinates().size() > bookshelf.getMaxAvailableSpace())  throw new IllegalArgumentException("Can't select more tiles.");
        for (int[] tile : canBeSelectedCoordinates) {
            if (Arrays.equals(tile, coordinates)) {
                selectedCoordinates.add(coordinates);
                updateCanBeSelectedCoordinates();
                return;
            }
        }
        System.out.println("item in given coordinates can't be selected");
    }

    /**
     * Pops the given coordinates from the Arraylist of selectedTiles and updates the canBeSelectedTiles ArrayList
     * @param coordinates   coordinates to be popped from the ArrayList of selectedTiles
     */
    public void deselectTile(int[] coordinates ){
        for (int i = 0; i < selectedCoordinates.size(); i++){
            if (coordinates[0] == selectedCoordinates.get(i)[0] && coordinates[1] == selectedCoordinates.get(i)[1]) {
                selectedCoordinates.remove(i);
                updateCanBeSelectedCoordinates();
                return;
            }
        }
        System.out.println("No coordinates in selectedTiles.");

    }

    /**
     * returns the ItemTiles from the coordinates in the arraylist selectedCoordinates
     * @return  ItemTiles from the coordinates in the arraylist selectedCoordinates
     */
    public ArrayList<ItemTile> getSelectedItemTiles() {
        for (int[] indices : selectedCoordinates) {
            selectedItemTiles.add(boardMatrix[indices[0]][indices[1]]);
            boardMatrix[indices[0]][indices[1]] = new ItemTile(ItemTileType.EMPTY);
        }
        selectedCoordinates.clear();
        return selectedItemTiles;
    }


}
