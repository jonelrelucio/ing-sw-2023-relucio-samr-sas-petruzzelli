package it.polimi.ingsw.distributed.integrated.messages;

import java.io.Serializable;

public abstract class Message implements Serializable {

    protected MessageType messageType;

    public MessageType getMessageType(){
        return messageType;
    }
}
