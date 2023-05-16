package client.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class TransferConfirmationMessageController {
    ClientMain client;
    Stage stage;

    public String title, destinationCompany;
    @FXML
    public Label messageLabel;
    @FXML
    void setClient(ClientMain client){
        this.client = client;
    }
    void setStage(Stage stage){
        this.stage = stage;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setDestinationCompany(String destinationCompany) {
        this.destinationCompany = destinationCompany;
    }

    @FXML
    void showTransferSuccessfulMessage(){
        this.stage.close();
        try {
            ClientMain.socketWrapper.write("Transfer movie");
            ClientMain.socketWrapper.write(title);
            ClientMain.socketWrapper.write(destinationCompany);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SuccessfulMessage.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        SuccessfulMessageController successfulMessageController = fxmlLoader.getController();
        successfulMessageController.setClient(client);
        successfulMessageController.messageLabel.setText("TRANSFER REQUEST SENT SUCCESSFULLY");
        stage.setScene(scene);
        stage.show();
        successfulMessageController.setStage(stage);
    }
    @FXML
    void back(){
        stage.close();
    }
}
