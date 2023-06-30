package it.polimi.ingsw.distributed.events.ViewEvents;

import it.polimi.ingsw.distributed.integrated.UtilitySerializer;
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

    /**
     * An identifier that is used to serialize/deserialize an object of a Serializable class.
     */
    private static final long serialVersionUID = 1234567L;

    /**
     * the serializable board matrix
     */
    private final ItemTileType[][] boardMatrix;

    /**
     * the serializable board matrix id
     */
    private final int[][] boardItemId;

    /**
     * the serializable player list
     */
    private final String[] playerList;

    /**
     * the serializable bookshelf list
     */
    private final ItemTileType[][][] bookshelfList;

    /**
     * the serializable bookshelf list id
     */
    private final int[][][] bookshelfListItemId;

    /**
     * the serializable can be selected coordinates
     */
    private final ArrayList<int[]> canBeSelectedCoordinates;

    /**
     * the serializable selected coordinates
     */
    private final ArrayList<int[]> selectedCoordinates;

    /**
     * the current player
     */
    private final String currentPlayer;

    /**
     * the serializable selected tiles
     */
    private final ArrayList<ItemTileType> selectedTiles;

    /**
     * the serializable selected tiles id
     */
    private final ArrayList<Integer> selectedTilesId;

    /**
     * the serializable personal goal card list
     */
    private final HashMap<String, ItemTileType[][]> personalGoalCardList;

    /**
     * the serializable common goal card deck
     */
    private final HashMap<Integer, Integer[]> commonGoalCardDeck;

    /**
     * the serializable personal goal player list id
     */
    private final HashMap<String, Integer> personalGoalCardPlayerListId;

    /**
     * the serializable points list
     */
    private final int[] pointsList;

    /**
     * chat
     */
    private final ArrayBlockingQueue<String> chat;

    /**
     * private message for chat
     */
    private final HashMap<String, String> privateMessage;

    /**
     * Initialize the GameModelView
     * @param gameModel the reference to the gameModel
     */
    public GameModelView(GameModel gameModel){
        this.boardMatrix = UtilitySerializer.serializeBoardMatrix(gameModel.getBoard().getBoardMatrix());
        this.boardItemId = UtilitySerializer.serializeItemId(gameModel.getBoard().getBoardMatrix());
        this.playerList = UtilitySerializer.serializeStringList(gameModel.getPlayerList());
        this.bookshelfList = UtilitySerializer.serializeArrayOfBookshelves(gameModel.getPlayerList());
        this.bookshelfListItemId = UtilitySerializer.serializeArrayOfItemId(gameModel.getPlayerList());
        this.selectedTiles = UtilitySerializer.serializeArrayOfItemTiles(gameModel.getCurrentPlayer().getSelectedItemTiles());
        this.selectedTilesId = UtilitySerializer.serializeArrayOfItemTilesId(gameModel.getCurrentPlayer().getSelectedItemTiles());
        this.personalGoalCardList = UtilitySerializer.serializeArrayOfPersonalGoalCards(gameModel.getPlayerList());
        this.commonGoalCardDeck = UtilitySerializer.serializeCommonGoalCardDeck(gameModel.getCommonGoalCardDeck().getDeck());
        this.pointsList = UtilitySerializer.serializePointsList(gameModel.getPlayerList());
        this.personalGoalCardPlayerListId = UtilitySerializer.serializeArrayPersonalGoalCardPlaterListId(gameModel.getPlayerList());
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

    /**
     * Getter for the list of the common goal cards
     * @return an HashMap filled with two common goal cards and their available points as Integer[]
     */
    public HashMap<Integer, Integer[]> getCommonGoalCardDeck() { return commonGoalCardDeck; }

    /**
     * Getter for the hashmap with the player and its personal goal card list
     * @return  a hashmap with the player and its personal goal card list
     */
    public HashMap<String, Integer> getPersonalGoalCardPlayerListId() { return personalGoalCardPlayerListId; }

    /**
     * Getter for the points list of each player
     * @return  an array of points
     */
    public int[] getPointsList() { return pointsList; }

    /**
     * Getter for the chat messages
     * @return the list of the last 10 messages in the chat
     */
    public ArrayBlockingQueue<String> getChat() { return chat; }

    /**
     * Getter for the ids of the tiles selected by the player
     * @return the ids of the tiles selected by the player
     */
    public ArrayList<Integer> getSelectedTilesId() { return selectedTilesId; }

    /**
     * Getter for the HashMap that contains the last private message of each player
     * @return an HashMap that has a player's nickname as key and the last received message as value
     */
    public HashMap<String, String> getPrivateMessage() { return privateMessage; }
}