package it.polimi.ingsw.networking;

import it.polimi.ingsw.events.ConnectedEvent;
import it.polimi.ingsw.events.GameEvent;
import it.polimi.ingsw.events.PlayerNameEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.util.CircularArrayList;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerImpl extends UnicastRemoteObject implements Server {

    private GameModel gameModel;
    private ArrayList<Client> clients;

    public ServerImpl(GameModel gameModel) throws RemoteException {
        super();
        clients = new ArrayList<>();
    }

    public ServerImpl() throws RemoteException{
        super();
    }

    public ServerImpl(GameModel gameModel, int port) throws RemoteException{
        super(port);
    }



    public ServerImpl(GameModel gameModel, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException{
        super(port, csf, ssf);
    }



    @Override
    public void register(Client client) throws RemoteException {
        //il client è il primo a connettersi
        ConnectedEvent connectedEvent = new ConnectedEvent("ConnectedEvent", ConnectedEvent.Status.OK);
        if(clients.isEmpty()){
            clients.add(client);
            client.update();
        }else if(clients.size() == gameModel.getNumOfPlayer()){
            //così questa linea non viene mai beccata, devo fare sì che i client possano provare a connettersi anche quando è tutto pieno (con un Runnable)
        }


    }

    @Override
    public void update(Client client, GameEvent arg) throws RemoteException {

    }
}
