package it.polimi.ingsw.model.events;

public class NewGame extends GameEvent {
    private String playerName;
    private Integer numOfPlayers;

    public NewGame(String playerName, Integer numOfPlayers) {
        super("registerPlayer");
        this.playerName = playerName;
        this.numOfPlayers = numOfPlayers;
    }
}
