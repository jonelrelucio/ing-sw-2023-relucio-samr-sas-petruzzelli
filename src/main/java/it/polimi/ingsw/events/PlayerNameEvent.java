package it.polimi.ingsw.events;

public class PlayerNameEvent extends GameEvent{
    private String playerName;

    public PlayerNameEvent(String eventName, String playerName){
        super(eventName);
        this.playerName = playerName;
    }



}
