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

public class CommonGoalCardDeck {
    private final HashMap<CommonGoalCard, Stack<Integer>> deck = new HashMap<>();

    private static final int[][] scoringTokenArray = {
            {4, 8},
            {4, 6, 8},
            {2, 4, 6, 8}
    };

    private Stack<Integer> buildScoringStack(int key){
        Stack<Integer> scoringTokenStack = new Stack<>();
        for (int i = 0; i < scoringTokenArray[key-2].length; i++){
            scoringTokenStack.push(scoringTokenArray[key-2][i]);
        }
        return scoringTokenStack;
    }

    public List<CommonGoalCard> getCommonGoalCardLIst() {

        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(CommonGoalCard.class, new CommonGoalCardDeserializer());
        Gson gson = builder.create();
        Reader reader;

        InputStream inputStream = getClass().getResourceAsStream("/json/CommonGoalCard.json");
        reader = new BufferedReader(new InputStreamReader(inputStream));
        return new ArrayList<>(Arrays.asList(gson.fromJson(reader, CommonGoalCard[].class)));
    }

    public CommonGoalCardDeck(int numOfPlayers) {

        List<CommonGoalCard> completeDeck = getCommonGoalCardLIst();

        Collections.shuffle(completeDeck);
        CommonGoalCard card1 = completeDeck.remove(0);
        CommonGoalCard card2 = completeDeck.remove(0);

        deck.put(card1, buildScoringStack(numOfPlayers));
        deck.put(card2, buildScoringStack(numOfPlayers));
    }

    public HashMap<CommonGoalCard, Stack<Integer>> getDeck() {
        return deck;
    }

    public int getScoringToken(CommonGoalCard card) {
        if (deck.get(card).isEmpty()) return 0;
        else return deck.get(card).pop();
    }

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
                player.setScore(player.getObtainedCommonGoalPoints()+getScoringToken(card));
            }
        }
        return player.getObtainedCommonGoalPoints();
    }

    public HashMap<CommonGoalCard, Stack<Integer>> getCommonGoalCardDeck() { return deck; }

}