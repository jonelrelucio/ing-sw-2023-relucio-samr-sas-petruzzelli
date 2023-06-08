package it.polimi.ingsw.distributed.integrated.messages;

public class SimpleTextMessage extends Message{
    private String message;

    public SimpleTextMessage(MessageType messageType, String message){
        this.messageType = messageType;
        this.message = message;
    }
    @Override
    public void processMessage() {
        getMessage();
    }

    public String getMessage(){
        return message;
    }
}
