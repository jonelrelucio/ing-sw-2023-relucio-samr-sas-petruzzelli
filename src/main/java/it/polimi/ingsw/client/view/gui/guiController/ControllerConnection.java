package it.polimi.ingsw.client.view.gui.guiController;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.integrated.RMIClient;
import it.polimi.ingsw.distributed.integrated.ServerStub;
import it.polimi.ingsw.distributed.integrated.SocketClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerConnection implements Initializable {
    @FXML
    private RadioButton RmiButton;
    @FXML
    private RadioButton SocketButton;
    @FXML
    private Label ConnectionChosen;
    @FXML
    private Button gotoChooseNickName;

    private ToggleGroup favLangToggleGroup;

    private static ViewGui viewGUI;




    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    private String connection;
    Server server = null;


    public void setViewGui(ViewGui viewGUI) {
        ControllerConnection.viewGUI = viewGUI;
    }

    public void goToNextScene(){
        if(Objects.equals(getConnection(), "r")){
            ConnectionChosen.setText("Scelto un client di tipo RMI");
            try{
                Registry reg = LocateRegistry.getRegistry();
                server = (Server) reg.lookup("server");
                //client = new RMIClient(server);//passare il server a RMIClient
                RMIClient client = new RMIClient(viewGUI, server);
                client.run();
                viewGUI.showWaitingPlayer();
            } catch (RemoteException e) {
                ConnectionChosen.setText("The connection mode chosen is not available at the moment\nChoose a different one");
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            }
        }else if(Objects.equals(getConnection(), "s")) {
            ConnectionChosen.setText("Scelto un client di tipo socket");
            server = new ServerStub("localhost", 1234);
            SocketClient client = new SocketClient((ServerStub) server, viewGUI);
            client.run();
        }

    }




    /**
     * This method will update the radioButtonLabel whenever a different
     * radio button is pushed
     */
    public void radioButtonChanged() {
        if (this.favLangToggleGroup.getSelectedToggle().equals(this.SocketButton)) {
            ConnectionChosen.setText("The connection mode chosen is Socket");
            setConnection("s");
        }

        if (this.favLangToggleGroup.getSelectedToggle().equals(this.RmiButton)) {
            ConnectionChosen.setText("The connection mode chosen is Rmi");
            setConnection("r");
        }
    }


    /**
     * setter connection  chosen
     */

    public void setConnectionChosen(){
        this.gotoChooseNickName.setDisable(false);
    }







    /**
     * initializes the objects before a scene starts
     * @param url
     * @param rb
     */

    public void initialize(URL url, ResourceBundle rb){
        //configuring the number of players selection
        ConnectionChosen.setText("");
        favLangToggleGroup = new ToggleGroup();
        this.SocketButton.setToggleGroup(favLangToggleGroup);
        this.RmiButton.setToggleGroup(favLangToggleGroup);
        //initialize the button for change scene
        this.gotoChooseNickName.setDisable(true);
    }

}

