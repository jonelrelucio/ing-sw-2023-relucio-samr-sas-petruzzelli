package it.polimi.ingsw.model.events;

public class RegisterPlayer extends GameEvent {
    private String playerName;
    private Integer numOfPlayers;

    public RegisterPlayer(String playerName, Integer numOfPlayers) {
        super("registerPlayer");
        this.playerName = playerName;
        this.numOfPlayers = numOfPlayers;
    }
}
