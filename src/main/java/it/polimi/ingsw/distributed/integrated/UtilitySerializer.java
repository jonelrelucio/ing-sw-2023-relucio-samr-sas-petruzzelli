package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.commonGoalCard.CommonGoalCard;
import it.polimi.ingsw.server.model.util.CircularArrayList;

import java.util.*;

public class UtilitySerializer {

    public static ItemTileType[][] serializeBoardMatrix(ItemTile[][] boardMatrix){
        ItemTileType[][] serializedBoard = new ItemTileType[boardMatrix.length][boardMatrix[0].length];
        for ( int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[0].length; j++) {
                serializedBoard[i][j] = boardMatrix[i][j].getItemTileType();
            }
        }
        return serializedBoard;
    }

    public static int[][] serializeItemId(ItemTile[][] idMatrix){
        int[][] boardId = new int[idMatrix.length][idMatrix[0].length];
        for (int i = 0; i < idMatrix.length; i++) {
            for (int j = 0; j < idMatrix[0].length; j++ ){
                boardId[i][j] = idMatrix[i][j].getId();
            }
        }
        return boardId;
    }


    public static String[] serializeStringList(CircularArrayList<Player> playerList) {
        String[] serializedStringArray = new String[playerList.size()];
        for (int i = 0; i < playerList.size(); i++) {
            serializedStringArray[i] = playerList.get(i).getNickname();
        }
        return serializedStringArray;
    }


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

    public static ArrayList<ItemTileType> serializeArrayOfItemTiles(ArrayList<ItemTile> selectedItemTiles) {
        ArrayList<ItemTileType> toReturn = new ArrayList<>();
        for (ItemTile itemTile : selectedItemTiles) {
            toReturn.add(itemTile.getItemTileType());
        }
        return toReturn;
    }

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

    public static int[] serializePointsList(CircularArrayList<Player> playerList) {
        int id = playerList.size();
        int[] toReturn = new int[id];
        for (int i = 0; i < id; i++ ){
            toReturn[i] = playerList.get(i).getScore();
        }
        return toReturn;
    }

    public static HashMap<Integer, Integer[]> serializeCommonGoalCardDeck(HashMap<CommonGoalCard, Stack<Integer>> commonGoalCardDeck) {
        HashMap<Integer, Integer[]> toReturn = new HashMap<>();
        int size = commonGoalCardDeck.size();
        for (Map.Entry<CommonGoalCard, Stack<Integer>> set : commonGoalCardDeck.entrySet()) {
            Integer key = set.getKey().getId();
            Integer[] array = set.getValue().toArray(new Integer[set.getValue().size()]);
            reverseArray(array);
            toReturn.put(key, array);
        }
        return toReturn;
    }

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

    public static HashMap<String, Integer> serializeArrayPersonalGoalCardPlaterListId(CircularArrayList<Player> playerList) {
        HashMap<String, Integer> personalGoalCardPlayerListId = new HashMap<>();
        for (int i = 0; i < playerList.size(); i++) {
            personalGoalCardPlayerListId.put(playerList.get(i).getNickname(), playerList.get(i).getPersonalGoalCard().getPersonalGoalCardId());
        }
        return personalGoalCardPlayerListId;
    }

    public static ArrayList<Integer> serializeArrayOfItemTilesId(ArrayList<ItemTile> selectedItemTiles) {
        ArrayList<Integer> toReturn = new ArrayList<>();
        for( ItemTile item : selectedItemTiles ){
            toReturn.add(item.getId());
        }
        return toReturn;
    }
}
