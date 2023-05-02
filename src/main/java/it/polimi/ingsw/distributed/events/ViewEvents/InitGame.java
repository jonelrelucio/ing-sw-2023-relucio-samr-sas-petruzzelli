package it.polimi.ingsw.distributed.events.ViewEvents;

import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.ItemTile.ItemTileType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.util.CircularArrayList;
import it.polimi.ingsw.model.util.Utility;

import java.util.ArrayList;

public class InitGame extends GameEvent {

    private ItemTileType[][] boardMatrix;
    private int[][] boardItemId;
    private String[] playerList;
    private ItemTileType[][][] bookshelfList;
    private int[][][] bookshelfListItemId;
    private ArrayList<int[]> canBeSelectedCoordinates;
    private ArrayList<int[]> selectedCoordinates;
    private String currentPlayer;


    public InitGame(Board board, CircularArrayList<Player> playerList, String currentPlayer, ArrayList<int[]> canBeSelectedCoordinates, ArrayList<int[]> selectedCoordinates){
        super("INIT_GAME");
        this.boardMatrix = Utility.serializeBoardMatrix(board.getBoardMatrix());
        this.boardItemId = Utility.serializeItemId(board.getBoardMatrix());
        this.playerList = Utility.serializeStringList(playerList);
        this.bookshelfList = Utility.serializeArrayOfBookshelves(playerList);
        this.bookshelfListItemId = Utility.serializeArrayOfItemId(playerList);
        this.canBeSelectedCoordinates = canBeSelectedCoordinates;
        this.selectedCoordinates = selectedCoordinates;
        this.currentPlayer = currentPlayer;
    }

    public ItemTileType[][] getBoardMatrix() { return boardMatrix; }
    public int[][] getBoardItemId() { return boardItemId; }
    public String[] getPlayerList() { return playerList; }
    public ItemTileType[][][] getBookshelfList() { return bookshelfList; }
    public int[][][] getBookshelfListItemId() { return bookshelfListItemId; }
    public ArrayList<int[]> getCanBeSelectedCoordinates() { return canBeSelectedCoordinates; }
    public ArrayList<int[]> getSelectedCoordinates() { return  selectedCoordinates; }
    public String getCurrentPlayer() { return currentPlayer; }

}
