package it.polimi.ingsw.client.view.gui.guiController;
import it.polimi.ingsw.AppClientRMI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.rmi.RemoteException;
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


    public void setViewGui(ViewGui viewGUI) {
        ControllerConnection.viewGUI = viewGUI;
    }

    public void goToNextScene(){
        if(Objects.equals(getConnection(), "r")){
            try {
                AppClientRMI.run(viewGUI);
            } catch (RemoteException e) {
                ConnectionChosen.setText("The connection mode chosen is not avalaible at the moment\nChoose a different one");
            }
        }else if(Objects.equals(getConnection(), "s")) {
            ConnectionChosen.setText("The connection mode chosen is not avalaible at the moment\nChoose a different one");
        }
        viewGUI.showWaitingPlayer();
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

