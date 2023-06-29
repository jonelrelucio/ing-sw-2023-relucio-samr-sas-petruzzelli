package it.polimi.ingsw.distributed.integrated.messages;

/**
 * Simple Message that extends the abstract class Message used for the socket connection
 */
public class SimpleTextMessage extends Message{

    /**
     * The message contained in the simple text message object
     */
    private final String message;

    /**
     * Constructor for the Simple Text Message
     * @param messageType   message type
     * @param message       message to put in the simple text message
     */
    public SimpleTextMessage(MessageType messageType, String message){
        this.messageType = messageType;
        this.message = message;
    }

    /**
     * Getter for the message
     * @return  the message
     */
    public String getMessage(){
        return message;
    }
}
