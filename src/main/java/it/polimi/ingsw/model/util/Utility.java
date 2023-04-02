package it.polimi.ingsw.model.util;

import com.google.gson.Gson;

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





}
