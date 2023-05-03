package it.polimi.ingsw.distributed;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ClientsHandler {

    ArrayList<Client> clients = new ArrayList<>();

    // TODO change contain, it checks
    public boolean checkJoin(Client client) {
        return !clients.contains(client);
    }
}
