package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.view.GameView;

import java.util.Scanner;

public class Game {
    GameModel model;
    GameView view;

    public Game(GameModel model, GameView view) {
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
