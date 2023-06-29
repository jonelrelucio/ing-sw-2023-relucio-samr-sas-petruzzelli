package it.polimi.ingsw.server.model.commonGoalCard;

import com.google.gson.*;
import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.*;
import java.util.*;
import java.io.BufferedReader;

/**
 * This class represents a deck of two common goal cards with the corresponding available stack of points
 */
public class CommonGoalCardDeck {
    /**
     * HashMap that has a common goal card as key and a stack of integer that represents the available point as value
     */
    private final HashMap<CommonGoalCard, Stack<Integer>> deck = new HashMap<>();
    /**
     * Array of int[] that contains the possible list of available point per card
     */
    private static final int[][] scoringTokenArray = {
            {4, 8},
            {4, 6, 8},
            {2, 4, 6, 8}
    };

    /**
     * This method build the stack of integer that represents the available points based on the number of players
     * @param key
     * @return a stack of Integer filled with the available points
     */
    private Stack<Integer> buildScoringStack(int key){
        Stack<Integer> scoringTokenStack = new Stack<>();
        for (int i = 0; i < scoringTokenArray[key-2].length; i++){
            scoringTokenStack.push(scoringTokenArray[key-2][i]);
        }
        return scoringTokenStack;
    }

    /**
     * This constructor deserialize the json that contains a list of common goal cards and extract two of them randomly,
     * then fill the 'deck' with the two cards and their stack of available points
     * @param numOfPlayers
     * @see #buildScoringStack(int)
     */
    public CommonGoalCardDeck(int numOfPlayers) {
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(CommonGoalCard.class, new CommonGoalCardDeserializer());
        Gson gson = builder.create();
        Reader reader;

        InputStream inputStream = getClass().getResourceAsStream("/json/CommonGoalCard.json");
        reader = new BufferedReader(new InputStreamReader(inputStream));

        List<CommonGoalCard> completeDeck = new ArrayList<>(Arrays.asList(gson.fromJson(reader, CommonGoalCard[].class)));

        Collections.shuffle(completeDeck);
        CommonGoalCard card1 = completeDeck.remove(0);
        CommonGoalCard card2 = completeDeck.remove(0);

        deck.put(card1, buildScoringStack(numOfPlayers));
        deck.put(card2, buildScoringStack(numOfPlayers));
    }

    /**
     * Getter for 'deck'
     * @return the HashMap with the two common goal cards and their available points
     */
    public HashMap<CommonGoalCard, Stack<Integer>> getDeck() {
        return deck;
    }

    /**
     * Remove the first available point from the stack of integer of the selected card
     * @param card
     * @return the first point available as int
     */
    public int getScoringToken(CommonGoalCard card) {
        if (deck.get(card).isEmpty()) return 0;
        else return deck.get(card).pop();
    }

    /**
     * Custom deserializer for common goal card.
     * Build the target class name based on the field "cardType" of the json objects
     */
    public static class CommonGoalCardDeserializer implements JsonDeserializer<CommonGoalCard> {
        @Override
        public CommonGoalCard deserialize(JsonElement json, Type typeOf, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = (JsonObject) json;
            JsonElement typeObj = jsonObject.get("cardType");

            if (typeObj != null) {
                String className = "it.polimi.ingsw.server.model.commonGoalCard." + typeObj.getAsString();
                try {
                    Class<?> cls = Class.forName(className);
                    return context.deserialize(json, cls);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            return null;
        }
    }

    /**
     * For each card in CommonGoalCardDeck: check if the current player's bookshelf match the scheme
     * if true: add the card to obtained common goal cards player list and add the scoring token value to the obtained common goal points
     * @param player                player to whom the points will be added
     * @return                      the obtainedCommonGoalPoints of the given player
     */
    public int getScore(Player player) {
        ItemTile[][] bookshelf = player.getBookshelf().getBookshelfMatrix();

        for (CommonGoalCard card : deck.keySet()) {
            if(!player.getObtainedCommonGoalCards().contains(card) && card.checkPattern(bookshelf)) {
                player.setObtainedCommonGoalCards(card);
                int points = player.getObtainedCommonGoalPoints() + getScoringToken(card);
                //player.setScore(points);
                player.setObtainedCommonGoalPoints(points);
            }
        }
        return player.getObtainedCommonGoalPoints();
    }
}