package it.polimi.ingsw.distributed.events.ViewEvents;

import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import it.polimi.ingsw.server.model.util.Utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class GameModelView implements Serializable {

    private ItemTileType[][] boardMatrix;
    private int[][] boardItemId;
    private String[] playerList;
    private ItemTileType[][][] bookshelfList;
    private int[][][] bookshelfListItemId;
    private ArrayList<int[]> canBeSelectedCoordinates;
    private ArrayList<int[]> selectedCoordinates;
    private String currentPlayer;
    private ArrayList<ItemTileType> selectedTiles;
    private HashMap<String, ItemTileType[][]> personalGoalCardList;
    private HashMap<String, int[][]> personalGooalCardListId;
    private HashMap<Integer, Integer[]> commonGoalCardDeck;
    private int[] pointsList;

    public GameModelView(GameModel gameModel){
        this.boardMatrix = Utility.serializeBoardMatrix(gameModel.getBoard().getBoardMatrix());
        this.boardItemId = Utility.serializeItemId(gameModel.getBoard().getBoardMatrix());
        this.playerList = Utility.serializeStringList(gameModel.getPlayerList());
        this.bookshelfList = Utility.serializeArrayOfBookshelves(gameModel.getPlayerList());
        this.bookshelfListItemId = Utility.serializeArrayOfItemId(gameModel.getPlayerList());
        this.selectedTiles = Utility.serializeArrayOfItemTiles(gameModel.getCurrentPlayer().getSelectedItemTiles());
        this.personalGoalCardList = Utility.serializeArrayOfPersonalGoalCards(gameModel.getPlayerList());
        this.personalGooalCardListId = Utility.serializeArrayOfPersonalGoalCardsId(gameModel.getPlayerList());
        this.commonGoalCardDeck = Utility.serializeCommonGoalCardDeck(gameModel.getCommonGoalCardDeck().getDeck());
        this.pointsList = Utility.serializePointsList(gameModel.getPlayerList());
        this.canBeSelectedCoordinates = gameModel.getBoard().getCanBeSelectedCoordinates();
        this.selectedCoordinates = gameModel.getBoard().getSelectedCoordinates();
        this.currentPlayer = gameModel.getCurrentPlayer().getNickname();
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
    public int[] getPointsList() { return pointsList; }
}