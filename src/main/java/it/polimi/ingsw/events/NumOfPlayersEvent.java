package it.polimi.ingsw.events;

public class NumOfPlayersEvent extends GameEvent{
    private int numOfPlayers;

    public NumOfPlayersEvent(String eventName, int numOfPlayers) {
        super(eventName);
        this.numOfPlayers = numOfPlayers;
    }


}
