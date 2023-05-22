package it.polimi.ingsw.distributed.integrated;

public abstract class GenericServer {
    protected static int maxConnections = 0;
    protected void incrementMaxConnection(){
        maxConnections++;
    }
    protected int getMaxConnections(){
        return maxConnections;
    }

    //implement canJoin a concrete method
}
