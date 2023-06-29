package it.polimi.ingsw.distributed.events.ViewEvents;

import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import it.polimi.ingsw.server.model.util.Utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * This class allow to access (read only) to the game model fields
 */
public class GameModelView implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private ItemTileType[][] boardMatrix;
    private int[][] boardItemId;
    private String[] playerList;
    private ItemTileType[][][] bookshelfList;
    private int[][][] bookshelfListItemId;
    private ArrayList<int[]> canBeSelectedCoordinates;
    private ArrayList<int[]> selectedCoordinates;
    private String currentPlayer;
    private ArrayList<ItemTileType> selectedTiles;
    private ArrayList<Integer> selectedTilesId;
    private HashMap<String, ItemTileType[][]> personalGoalCardList;
    private HashMap<String, int[][]> personalGooalCardListId;
    private HashMap<Integer, Integer[]> commonGoalCardDeck;
    private HashMap<String, Integer> personalGoalCardPlayerListId;
    private int[] pointsList;
    private ArrayBlockingQueue<String> chat;
    private HashMap<String, String> privateMessage;

    /**
     * Initialize the GameModelView
     * @param gameModel
     */
    public GameModelView(GameModel gameModel){
        this.boardMatrix = Utility.serializeBoardMatrix(gameModel.getBoard().getBoardMatrix());
        this.boardItemId = Utility.serializeItemId(gameModel.getBoard().getBoardMatrix());
        this.playerList = Utility.serializeStringList(gameModel.getPlayerList());
        this.bookshelfList = Utility.serializeArrayOfBookshelves(gameModel.getPlayerList());
        this.bookshelfListItemId = Utility.serializeArrayOfItemId(gameModel.getPlayerList());
        this.selectedTiles = Utility.serializeArrayOfItemTiles(gameModel.getCurrentPlayer().getSelectedItemTiles());
        this.selectedTilesId = Utility.serializeArrayOfItemTilesId(gameModel.getCurrentPlayer().getSelectedItemTiles());
        this.personalGoalCardList = Utility.serializeArrayOfPersonalGoalCards(gameModel.getPlayerList());
        this.personalGooalCardListId = Utility.serializeArrayOfPersonalGoalCardsId(gameModel.getPlayerList());
        this.commonGoalCardDeck = Utility.serializeCommonGoalCardDeck(gameModel.getCommonGoalCardDeck().getDeck());
        this.pointsList = Utility.serializePointsList(gameModel.getPlayerList());
        this.personalGoalCardPlayerListId = Utility.serializeArrayPersonalGoalCardPlaterListId(gameModel.getPlayerList());
        this.canBeSelectedCoordinates = gameModel.getBoard().getCanBeSelectedCoordinates();
        this.selectedCoordinates = gameModel.getBoard().getSelectedCoordinates();
        this.currentPlayer = gameModel.getCurrentPlayer().getNickname();
        this.chat = gameModel.getChat();
        this.privateMessage = gameModel.getPrivateMessageMap();
    }

    /**
     * Getter for the board game matrix
     * @return a matrix filled with item tiles
     */
    public ItemTileType[][] getBoardMatrix() { return boardMatrix; }
    public int[][] getBoardItemId() { return boardItemId; }

    /**
     * Getter for the players list
     * @return an array of nickname as String[]
     */
    public String[] getPlayerList() { return playerList; }

    /**
     * Getter for the bookshelves of the players
     * @return an array of matrix that represents the bookshelves of the players
     */
    public ItemTileType[][][] getBookshelfList() { return bookshelfList; }
    public int[][][] getBookshelfListItemId() { return bookshelfListItemId; }

    /**
     * Getter for the item tiles that could be chosen by a player
     * @return a list of coordinates as int[]
     */
    public ArrayList<int[]> getCanBeSelectedCoordinates() { return canBeSelectedCoordinates; }

    /**
     * Getter for the item tiles selected by the player
     * @return a list of coordinates as int[]
     */
    public ArrayList<int[]> getSelectedCoordinates() { return  selectedCoordinates; }

    /**
     * Getter for the current player
     * @return the nickname of the current player as String
     */
    public String getCurrentPlayer() { return currentPlayer; }

    /**
     * Getter for the item tiles selected by the player
     * @return a list of the item tiles type
     */
    public ArrayList<ItemTileType> getSelectedTiles() { return selectedTiles; }

    /**
     * Getter for the HashMap that contains the player's personal goal cards
     * @return the list of the personal goal cards as {@code HashMap<String, ItemTileType[][]>}
     */
    public HashMap<String, ItemTileType[][]> getPersonalGoalCardList() { return personalGoalCardList; }
    public HashMap<String, int[][]> getPersonalGoalCardListId() { return personalGooalCardListId; }

    /**
     * Getter for the list of the common goal cards
     * @return an HashMap filled with two common goal cards and their available points as Integer[]
     */
    public HashMap<Integer, Integer[]> getCommonGoalCardDeck() { return commonGoalCardDeck; }
    public HashMap<String, Integer> getPersonalGoalCardPlayerListId() { return personalGoalCardPlayerListId; }
    public int[] getPointsList() { return pointsList; }

    /**
     * Getter for the chat messages
     * @return the list of the last 10 messages in the chat
     */
    public ArrayBlockingQueue<String> getChat() { return chat; }

    /**
     * Getter for the ids of the tiles selected by the player
     * @return
     */
    public ArrayList<Integer> getSelectedTilesId() { return selectedTilesId; }

    /**
     * Getter for the HashMap that contains the last private message of each player
     * @return an HashMap that has a player's nickname as key and the last received message as value
     */
    public HashMap<String, String> getPrivateMessage() { return privateMessage; }
}