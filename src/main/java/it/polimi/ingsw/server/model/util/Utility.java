package it.polimi.ingsw.server.model.util;

import com.google.gson.Gson;
import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import it.polimi.ingsw.server.model.Player;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Utility {

    /**
     *
     * @param path  the path of the json file from the resources folder
     * @return      content of the given json file converted to string
     */
    public static String getJsonFromPath(String path){
        InputStream is = Utility.class.getResourceAsStream(path);
        String json = null;
        if (is == null) throw new IllegalArgumentException("File not Found: "+path);
        try {
            json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     *
     * @param path      the path of the json file from the resources folder
     * @param myClass   the class which the json file will be deserialized to
     * @return          the object deserialized from the json file
     */
    public static Object deserializeJsonToObject(String path, Class<?> myClass){
        if (myClass == null ) throw new IllegalArgumentException("Illegal Class Argument ");
        String json = getJsonFromPath(path);
        Gson gson = new Gson();
        return gson.fromJson(json, myClass);
    }

    /**
     * Converts A List<List<Integer>> to an int[][]
     * @param list  is a List<List<Integer>>
     * @return      converted int[][]
     */
    public static int[][] convertListListToArrayArray(List<List<Integer>> list) {
        int[][] arr = new int[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            List<Integer> innerList = list.get(i);
            arr[i] = innerList.stream().mapToInt(Integer::intValue).toArray();
        }
        return arr;

    }



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
            for ( int j = 0; i < row; i++) {
                for (int k = 0; j < col; j++) {
                    toReturn[i][j][k] = playerList.get(i).getBookshelf().getBookshelfMatrix()[j][k].getId();
                }
            }
        }
        return toReturn;
    }
}
