package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.commonGoalCard.CommonGoalCard;
import it.polimi.ingsw.server.model.util.CircularArrayList;

import java.util.*;

/**
Class used to transform the game model components into primitive types for serialization
 **/
public class UtilitySerializer {

    /**
     * Used to make the board matrix serializable
     * @param boardMatrix   the boardMatrix which is an ItemTile[][]
     * @return              the serializable board
     */
    public static ItemTileType[][] serializeBoardMatrix(ItemTile[][] boardMatrix){
        ItemTileType[][] serializedBoard = new ItemTileType[boardMatrix.length][boardMatrix[0].length];
        for ( int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[0].length; j++) {
                serializedBoard[i][j] = boardMatrix[i][j].getItemTileType();
            }
        }
        return serializedBoard;
    }

    /**
     * Used to make the board matrix id serializable
     * @param boardMatrix  the board matrix which is an ItemTile[][]
     * @return              the serializable board id
     */
    public static int[][] serializeItemId(ItemTile[][] boardMatrix){
        int[][] boardId = new int[boardMatrix.length][boardMatrix[0].length];
        for (int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[0].length; j++ ){
                boardId[i][j] = boardMatrix[i][j].getId();
            }
        }
        return boardId;
    }

    /**
     * Used to make the player list serializable
     * @param playerList    the player list
     * @return              the serializable player list
     */
    public static String[] serializeStringList(CircularArrayList<Player> playerList) {
        String[] serializedStringArray = new String[playerList.size()];
        for (int i = 0; i < playerList.size(); i++) {
            serializedStringArray[i] = playerList.get(i).getNickname();
        }
        return serializedStringArray;
    }

    /**
     * Used to make the array of bookshelves serializable
     * @param playerList    the player list
     * @return              the serializable array of bookshelves
     */
    public static ItemTileType[][][] serializeArrayOfBookshelves(CircularArrayList<Player> playerList) {
        int id = playerList.size();
        int row = playerList.get(0).getBookshelf().getBookshelfMatrix().length;
        int col = playerList.get(0).getBookshelf().getBookshelfMatrix()[0].length;
        ItemTileType[][][] toReturn = new ItemTileType[id][row][col];
        for (int i = 0; i < id; i++){
            for ( int j = 0; j < row; j++) {
                for (int k = 0; k < col; k++) {
                    toReturn[i][j][k] = playerList.get(i).getBookshelf().getBookshelfMatrix()[j][k].getItemTileType();
                }
            }
        }
        return toReturn;
    }

    /**
     * Used to make the array of bookshelves id serializable
     * @param playerList    the player list
     * @return              the serializable bookshelves id
     */
    public static int[][][] serializeArrayOfItemId(CircularArrayList<Player> playerList) {
        int id = playerList.size();
        int row = playerList.get(0).getBookshelf().getBookshelfMatrix().length;
        int col = playerList.get(0).getBookshelf().getBookshelfMatrix()[0].length;
        int[][][] toReturn = new int[id][row][col];
        for (int i = 0; i < id; i++){
            for ( int j = 0; j < row; j++) {
                for (int k = 0; k < col; k++) {
                    toReturn[i][j][k] = playerList.get(i).getBookshelf().getBookshelfMatrix()[j][k].getId();
                }
            }
        }
        return toReturn;
    }

    /**
     * Used to make the selected item tiles serializable
     * @param selectedItemTiles the selected item tiles
     * @return  the serialized array of item tiles
     */
    public static ArrayList<ItemTileType> serializeArrayOfItemTiles(ArrayList<ItemTile> selectedItemTiles) {
        ArrayList<ItemTileType> toReturn = new ArrayList<>();
        for (ItemTile itemTile : selectedItemTiles) {
            toReturn.add(itemTile.getItemTileType());
        }
        return toReturn;
    }

    /**
     * Used to make the array of personal goal cards serializable
     * @param playerList    the player list
     * @return              the serializable personal goal cards
     */
    public static HashMap<String, ItemTileType[][]> serializeArrayOfPersonalGoalCards(CircularArrayList<Player> playerList) {
        int id = playerList.size();
        int row = playerList.get(0).getPersonalGoalCard().getPersonalGoalCardMatrix().length;
        int col = playerList.get(0).getPersonalGoalCard().getPersonalGoalCardMatrix()[0].length;
        HashMap<String, ItemTileType[][]> toReturn = new HashMap<>();
        for (int i = 0; i < id; i++){
            ItemTileType[][] personalGoalCard = new ItemTileType[row][col];
            String name = playerList.get(i).getNickname();
            for ( int j = 0; j < row; j++) {
                for (int k = 0; k < col; k++) {
                    personalGoalCard[j][k] = playerList.get(i).getPersonalGoalCard().getPersonalGoalCardMatrix()[j][k].getItemTileType();
                }
            }
            toReturn.put(name, personalGoalCard);
        }
        return toReturn;
    }

    /**
     * Used to make the array of points serializable
     * @param playerList    the playerList
     * @return              the serializable points
     */
    public static int[] serializePointsList(CircularArrayList<Player> playerList) {
        int id = playerList.size();
        int[] toReturn = new int[id];
        for (int i = 0; i < id; i++ ){
            toReturn[i] = playerList.get(i).getScore();
        }
        return toReturn;
    }

    /**
     * Used to make the common goal card deck serializable
     * @param commonGoalCardDeck    the common goal card deck
     * @return                      the serializable common goal card deck
     */
    public static HashMap<Integer, Integer[]> serializeCommonGoalCardDeck(HashMap<CommonGoalCard, Stack<Integer>> commonGoalCardDeck) {
        HashMap<Integer, Integer[]> toReturn = new HashMap<>();
        for (Map.Entry<CommonGoalCard, Stack<Integer>> set : commonGoalCardDeck.entrySet()) {
            Integer key = set.getKey().getId();
            Integer[] array = set.getValue().toArray(new Integer[set.getValue().size()]);
            reverseArray(array);
            toReturn.put(key, array);
        }
        return toReturn;
    }

    /**
     * Utility function used to reverse the order of the array
     * @param array the array to be reversed
     */
    private static void reverseArray(Integer[] array) {
        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            Integer temp = array[left];
            array[left] = array[right];
            array[right] = temp;

            left++;
            right--;
        }
    }

    /**
     * Used to make the array of personal goal card id (the id indicates the image) serializable
     * @param playerList    the player list
     * @return              the serializable personal goal card id
     */
    public static HashMap<String, Integer> serializeArrayPersonalGoalCardPlaterListId(CircularArrayList<Player> playerList) {
        HashMap<String, Integer> personalGoalCardPlayerListId = new HashMap<>();
        for (Player player : playerList) {
            personalGoalCardPlayerListId.put(player.getNickname(), player.getPersonalGoalCard().getPersonalGoalCardId());
        }
        return personalGoalCardPlayerListId;
    }

    /**
     * Used to make the array of selectedItemTiles id serializable
     * @param selectedItemTiles the selected Item tiles
     * @return                  the serializable selected item tiles id
     */
    public static ArrayList<Integer> serializeArrayOfItemTilesId(ArrayList<ItemTile> selectedItemTiles) {
        ArrayList<Integer> toReturn = new ArrayList<>();
        for( ItemTile item : selectedItemTiles ){
            toReturn.add(item.getId());
        }
        return toReturn;
    }
}
