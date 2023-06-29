package it.polimi.ingsw.distributed.integrated.messages;

public class SimpleTextMessage extends Message{
    private String message;

    public SimpleTextMessage(MessageType messageType, String message){
        this.messageType = messageType;
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
