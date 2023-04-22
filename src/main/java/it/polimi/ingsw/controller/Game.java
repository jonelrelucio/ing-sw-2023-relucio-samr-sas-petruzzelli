package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.view.CLI;

public class Game {
    GameModel model;
    CLI view;

    public Game(GameModel model, CLI view) {
        this.model = model;
        this.view = view;
    }

    public void play() {
        initgame();
        midgame();

    }

    public void initgame() {
        model.initCurrentPlayer();
    }

    public void midgame() {
        while(true){
            model.getCurrentPlayer().selectCoordinates(view.askPlayerSelectedTiles());
        }
    }
}
