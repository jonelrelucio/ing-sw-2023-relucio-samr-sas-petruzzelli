package it.polimi.ingsw.model.commonGoalCard;

import com.google.gson.*;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.*;
import java.util.*;

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

    public CommonGoalCardDeck(int numOfPlayers) throws IOException {
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(CommonGoalCard.class, new CommonGoalCardDeserializer());
        Gson gson = builder.create();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/it/polimi/ingsw/model/commonGoalCard/CommonGoalCard.json"));

        List<CommonGoalCard> completeDeck = new ArrayList<>(Arrays.asList(gson.fromJson(reader, CommonGoalCard[].class)));

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
                String cardType = typeObj.getAsString();

                switch (cardType) {
                    case "CommonGoalExactShape" -> {
                        return context.deserialize(json, CommonGoalExactShape.class);
                    }
                    case "CommonGoalShape" -> {
                        return context.deserialize(json, CommonGoalShape.class);
                    }
                    case "CommonGoalSameTypeGroup" -> {
                        return context.deserialize(json, CommonGoalSameTypeGroup.class);
                    }
                    case "CommonGoalDifferentType" -> {
                        return context.deserialize(json, CommonGoalDifferentType.class);
                    }
                }
            }

            return null;
        }
    }

}