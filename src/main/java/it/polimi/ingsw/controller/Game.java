package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.view.View;

public class Game implements Runnable{
    private final GameModel model;
    private final View view;

    public Game(GameModel model, View view){
        this.model = model;
        this.view = view;
    }


    @Override
    public void run() {

    }
}
