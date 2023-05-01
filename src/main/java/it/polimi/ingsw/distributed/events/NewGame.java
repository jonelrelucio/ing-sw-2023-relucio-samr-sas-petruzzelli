package it.polimi.ingsw.distributed.events;


public class NewGame extends GameEvent {
    private String playerName;
    private Integer numOfPlayers;

    public NewGame(int numOfPlayers, String playerName ) {
        super("NEW_GAME");
        this.playerName = playerName;
        this.numOfPlayers = numOfPlayers;
    }

    public String getPlayerName() {
        return playerName;
    }
    public Integer getNumOfPlayers() { return numOfPlayers; }

}