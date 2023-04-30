package it.polimi.ingsw.controller.events;

public class SendMessage extends GameEvent {
    private String message;

    public SendMessage(String message) {
        super("sendMessage");
        this.message = message;
    }
}
