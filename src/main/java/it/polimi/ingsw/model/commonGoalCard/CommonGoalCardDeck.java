package it.polimi.ingsw.model.commonGoalCard;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
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
        Stack<CommonGoalCard> completeDeck = new Stack<>();

        Gson gson = new Gson();

        Reader reader = Files.newBufferedReader(Paths.get("CommonGoalExactShape.json"));
        List<CommonGoalCard> exactShape = new ArrayList<>(Arrays.asList(gson.fromJson(reader, CommonGoalExactShape.class)));

        reader = Files.newBufferedReader(Paths.get("CommonGoalShape.json"));
        List<CommonGoalCard> shape = new ArrayList<>(Arrays.asList(gson.fromJson(reader, CommonGoalShape.class)));

        reader = Files.newBufferedReader(Paths.get("CommonGoalSameTypeGroup.json"));
        List<CommonGoalCard> group = new ArrayList<>(Arrays.asList(gson.fromJson(reader, CommonGoalSameTypeGroup.class)));

        reader = Files.newBufferedReader(Paths.get("CommonGoalDifferentType.json"));
        List<CommonGoalCard> different = new ArrayList<>(Arrays.asList(gson.fromJson(reader, CommonGoalDifferentType.class)));

        completeDeck.addAll(exactShape);
        completeDeck.addAll(shape);
        completeDeck.addAll(group);
        completeDeck.addAll(different);

        Collections.shuffle(completeDeck);
        CommonGoalCard card1 = completeDeck.pop();

        Collections.shuffle(completeDeck);
        CommonGoalCard card2 = completeDeck.pop();

        deck.put(card1, buildScoringStack(numOfPlayers));
        deck.put(card2, buildScoringStack(numOfPlayers));
    }

    public HashMap<CommonGoalCard, Stack<Integer>> getDeck() {
        return deck;
    }

    public int getScoringToken(CommonGoalCard card) {
        return deck.get(card).pop();
    }

    public static void main(String[] args) {
        String filename="CommonGoalExactShape.json";
        Path pathToFile = Paths.get(filename);
        System.out.println(pathToFile.toAbsolutePath());
    }

}