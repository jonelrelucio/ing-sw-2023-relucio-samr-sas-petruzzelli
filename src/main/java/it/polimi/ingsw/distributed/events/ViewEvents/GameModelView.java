package it.polimi.ingsw.distributed.events.ViewEvents;

import it.polimi.ingsw.distributed.integrated.UtilitySerializer;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import it.polimi.ingsw.server.model.util.Utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

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

    public GameModelView(GameModel gameModel){
        this.boardMatrix = UtilitySerializer.serializeBoardMatrix(gameModel.getBoard().getBoardMatrix());
        this.boardItemId = UtilitySerializer.serializeItemId(gameModel.getBoard().getBoardMatrix());
        this.playerList = UtilitySerializer.serializeStringList(gameModel.getPlayerList());
        this.bookshelfList = UtilitySerializer.serializeArrayOfBookshelves(gameModel.getPlayerList());
        this.bookshelfListItemId = UtilitySerializer.serializeArrayOfItemId(gameModel.getPlayerList());
        this.selectedTiles = UtilitySerializer.serializeArrayOfItemTiles(gameModel.getCurrentPlayer().getSelectedItemTiles());
        this.selectedTilesId = UtilitySerializer.serializeArrayOfItemTilesId(gameModel.getCurrentPlayer().getSelectedItemTiles());
        this.personalGoalCardList = UtilitySerializer.serializeArrayOfPersonalGoalCards(gameModel.getPlayerList());
        this.personalGooalCardListId = UtilitySerializer.serializeArrayOfPersonalGoalCardsId(gameModel.getPlayerList());
        this.commonGoalCardDeck = UtilitySerializer.serializeCommonGoalCardDeck(gameModel.getCommonGoalCardDeck().getDeck());
        this.pointsList = UtilitySerializer.serializePointsList(gameModel.getPlayerList());
        this.personalGoalCardPlayerListId = UtilitySerializer.serializeArrayPersonalGoalCardPlaterListId(gameModel.getPlayerList());
        this.canBeSelectedCoordinates = gameModel.getBoard().getCanBeSelectedCoordinates();
        this.selectedCoordinates = gameModel.getBoard().getSelectedCoordinates();
        this.currentPlayer = gameModel.getCurrentPlayer().getNickname();
        this.chat = gameModel.getChat();
    }

    public ItemTileType[][] getBoardMatrix() { return boardMatrix; }
    public int[][] getBoardItemId() { return boardItemId; }
    public String[] getPlayerList() { return playerList; }
    public ItemTileType[][][] getBookshelfList() { return bookshelfList; }
    public int[][][] getBookshelfListItemId() { return bookshelfListItemId; }
    public ArrayList<int[]> getCanBeSelectedCoordinates() { return canBeSelectedCoordinates; }
    public ArrayList<int[]> getSelectedCoordinates() { return  selectedCoordinates; }
    public String getCurrentPlayer() { return currentPlayer; }
    public ArrayList<ItemTileType> getSelectedTiles() { return selectedTiles; }
    public HashMap<String, ItemTileType[][]> getPersonalGoalCardList() { return personalGoalCardList; }
    public HashMap<String, int[][]> getPersonalGoalCardListId() { return personalGooalCardListId; }
    public HashMap<Integer, Integer[]> getCommonGoalCardDeck() { return commonGoalCardDeck; }
    public HashMap<String, Integer> getPersonalGoalCardPlayerListId() { return personalGoalCardPlayerListId; }
    public int[] getPointsList() { return pointsList; }
    public ArrayBlockingQueue<String> getChat() { return chat; }
    public ArrayList<Integer> getSelectedTilesId() { return selectedTilesId; }
}