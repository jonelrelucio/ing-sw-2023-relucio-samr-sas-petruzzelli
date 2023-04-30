package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commonGoalCard.CommonGoalCardDeck;
import it.polimi.ingsw.model.util.CircularArrayList;

public class GameModelView  {
    private final GameModel model;

    public GameModelView(GameModel model) {
        if (model == null) throw new IllegalArgumentException();
        this.model = model;
    }

    public int getNumOfPlayer() {
        return model.getNumOfPlayer();
    }

    public CircularArrayList<Player> getPlayerList() {
        return model.getPlayerList();
    }

    public CommonGoalCardDeck getCommonGoalCardDeck() {
        return model.getCommonGoalCardDeck();
    }

    public Board getBoard() {
        return model.getBoard();
    }

    public int getNumOfRounds() {
        return model.getNumOfRounds();
    }

    public Player getCurrentPlayer() {
        return model.getCurrentPlayer();
    }

}
