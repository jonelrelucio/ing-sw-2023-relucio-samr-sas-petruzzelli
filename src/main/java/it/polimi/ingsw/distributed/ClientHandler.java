package it.polimi.ingsw.distributed;

import java.util.ArrayList;

/**
 * This is used to manage the connected clients.
 * So the server doesn't have to get the client data remotely
 */

public class ClientHandler {

    private final Client client;
    private final String username;

    public ClientHandler(Client client, String username) {
        this.client = client;
        this.username = username;
    }

    public Client getClient(){
        return client;
    }

    public String getUsername(){
        return username;
    }

}
