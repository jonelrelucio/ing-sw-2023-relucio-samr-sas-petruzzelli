package it.polimi.ingsw.events;

public class PlayerEvent extends GameEvent{
    private String name;
    private int numOfPlayers;

    public PlayerEvent(String name){
        super(name);
        numOfPlayers = 0;   //0 corrisponde a null
    }

    public PlayerEvent(String name,int numOfPlayers){
        super(name);
        this.numOfPlayers = numOfPlayers;
    }


}
