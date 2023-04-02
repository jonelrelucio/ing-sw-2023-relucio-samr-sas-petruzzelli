package it.polimi.ingsw.distributed;

public interface Server {
    void register( Client client );
    void update(Client client);
}
