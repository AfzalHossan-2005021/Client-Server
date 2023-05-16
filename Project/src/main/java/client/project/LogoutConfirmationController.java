package client.project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class LogoutConfirmationController {
    ClientMain client;
    Stage stage;
    @FXML
    Label messageLabel;

    public void setClient(ClientMain client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    void logout(){
        try {
            stage.close();
            ClientMain.socketWrapper.write("Close");
            ClientMain.socketWrapper.closeConnection();
            client.showLoginPage();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    @FXML
    void back(){
        stage.close();
    }
}
