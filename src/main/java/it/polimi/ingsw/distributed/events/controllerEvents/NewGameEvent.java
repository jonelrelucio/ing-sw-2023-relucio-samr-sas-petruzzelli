package it.polimi.ingsw.distributed.events.controllerEvents;


import it.polimi.ingsw.distributed.events.GameEvent;

public class NewGameEvent extends GameEvent {
    private String playerName;
    private Integer numOfPlayers;

    public NewGameEvent(int numOfPlayers, String playerName ) {
        super("NEW_GAME");
        this.playerName = playerName;
        this.numOfPlayers = numOfPlayers;
    }

    public String getPlayerName() {
        return playerName;
    }
    public Integer getNumOfPlayers() { return numOfPlayers; }

}
