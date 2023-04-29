package it.polimi.ingsw.events;

public class ConnectedEvent extends GameEvent{


    public enum Status{
        OK, FAILED
    }

    private Status status;

    public ConnectedEvent(String eventName, Status status){
        super(eventName);
        this.status = status;
    }

    public Status getStatus(){
        return status;
    }

}
