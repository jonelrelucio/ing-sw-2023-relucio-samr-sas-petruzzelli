package it.polimi.ingsw.distributed.integrated.messages;

import it.polimi.ingsw.distributed.events.GameEvent;

public class ClientUpdateMessage extends Message{

    private GameEvent gameEvent;

    public ClientUpdateMessage(GameEvent event){
        this.messageType = MessageType.CLIENT_UPDATE_MESSAGE;
        this.gameEvent = event;
    }
    @Override
    public void processMessage() {

    }

    public GameEvent getGameEvent(){
        return this.gameEvent;
    }


}
