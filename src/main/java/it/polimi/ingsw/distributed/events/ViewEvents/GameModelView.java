package it.polimi.ingsw.distributed.events.ViewEvents;

import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.util.CircularArrayList;
import it.polimi.ingsw.server.model.util.Utility;

import java.util.ArrayList;

public class GameModelView extends GameEvent {

    private ItemTileType[][] boardMatrix;
    private int[][] boardItemId;
    private String[] playerList;
    private ItemTileType[][][] bookshelfList;
    private int[][][] bookshelfListItemId;
    private ArrayList<int[]> canBeSelectedCoordinates;
    private ArrayList<int[]> selectedCoordinates;
    private String currentPlayer;
    private boolean endTurn;
    private ArrayList<ItemTileType> selectedTiles;


    public GameModelView(Board board, CircularArrayList<Player> playerList, Player currentPlayer, boolean endTurn){
        this.boardMatrix = Utility.serializeBoardMatrix(board.getBoardMatrix());
        this.boardItemId = Utility.serializeItemId(board.getBoardMatrix());
        this.playerList = Utility.serializeStringList(playerList);
        this.bookshelfList = Utility.serializeArrayOfBookshelves(playerList);
        this.bookshelfListItemId = Utility.serializeArrayOfItemId(playerList);
        this.selectedTiles = Utility.serializeArrayOfItemTiles(currentPlayer.getSelectedItemTiles());
        this.canBeSelectedCoordinates = board.getCanBeSelectedCoordinates();
        this.selectedCoordinates = board.getSelectedCoordinates();
        this.currentPlayer = currentPlayer.getNickname();
        this.endTurn = endTurn;
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
    public boolean isEndTurn() {return endTurn;}
}
