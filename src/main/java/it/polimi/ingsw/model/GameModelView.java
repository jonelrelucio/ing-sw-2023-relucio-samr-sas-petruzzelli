package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commonGoalCard.CommonGoalCardDeck;
import it.polimi.ingsw.model.events.GameEvent;
import it.polimi.ingsw.model.util.CircularArrayList;
import it.polimi.ingsw.util.Observable;
import it.polimi.ingsw.util.Observer;

public class GameModelView extends Observable<GameEvent> implements Observer<GameModel, GameEvent> {
    private final GameModel model;

    public GameModelView(GameModel model) {
        if (model == null) throw new IllegalArgumentException();
        this.model = model;
        model.addObserver(this);
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

    @Override
    public void update(GameModel o, GameEvent arg) {
        setChanged();
        notifyObservers(arg);
    }
}
