package it.polimi.ingsw.model.events;

public class SendMessage extends GameEvent {
    private String message;

    public SendMessage(String message) {
        super("sendMessage");
        this.message = message;
    }
}
