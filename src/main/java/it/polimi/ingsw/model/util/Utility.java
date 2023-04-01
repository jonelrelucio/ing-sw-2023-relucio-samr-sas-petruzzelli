package it.polimi.ingsw.model.util;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Utility {

    // Gets the json path and returns the json file content
    public static String getJsonFromPath(String path){
        InputStream is = Utility.class.getClassLoader().getResourceAsStream(path);
        String json = null;
        if (is == null) throw new IllegalArgumentException("File not Found: "+path);
        // Converts the content of the path into a json string
        try {
            json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    // Deserializes json string to object of any class and returns the object
    public static Object deserializeJsonToObject(String path, Class<?> myClass){
        if (myClass == null ) throw new IllegalArgumentException("Illegal Class Argument ");
        String json = getJsonFromPath(path);
        Gson gson = new Gson();
        return gson.fromJson(json, myClass);
    }

    // Converts List<List<Integer>> to int[][]
    public static int[][] convertListListToArrayArray(List<List<Integer>> list) {
        int[][] arr = new int[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            List<Integer> innerList = list.get(i);
            arr[i] = innerList.stream().mapToInt(Integer::intValue).toArray();
        }
        return arr;
    }





}
