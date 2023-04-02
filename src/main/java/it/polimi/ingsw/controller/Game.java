package it.polimi.ingsw.controller;

import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.model.GameModel;

public class Game {
    private final GameModel model;
    private final Client client;

    public Game(GameModel model, Client client){
        this.model = model;
        this.client = client;
    }


}
