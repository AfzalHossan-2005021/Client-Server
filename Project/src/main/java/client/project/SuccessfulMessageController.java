package client.project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class SuccessfulMessageController {
    ClientMain client;
    Stage stage;
    @FXML
    public Label messageLabel;
    @FXML
    void gotoNext(){
        try {
            stage.close();
            client.showCompanyHome();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    void setClient(ClientMain client){
        this.client = client;
    }
    void setStage(Stage stage){
        this.stage = stage;
    }
}
