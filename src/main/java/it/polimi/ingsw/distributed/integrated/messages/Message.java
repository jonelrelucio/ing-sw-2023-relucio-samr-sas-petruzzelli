package it.polimi.ingsw.distributed.integrated.messages;

import java.io.Serializable;

/**
 * Abstract message used for socket connections
 */
public abstract class Message implements Serializable {

    /**
     * The type of message
     */
    protected MessageType messageType;

    /**
     * Getter of the message type
     * @return  the message type of the message
     */
    public MessageType getMessageType(){
        return messageType;
    }
}
