package client.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class TransferMoviePageController {
    ClientMain client;
    @FXML
    TextField titleTextField, destinationCompanyTextField;
    @FXML
    Label titleNoticeLabel, companyNoticeLabel;
    String title, destinationCompany;
    void setClient(ClientMain client){
        this.client = client;
    }

    @FXML
    void movieTransferRequest(){

        title = titleTextField.getText();
        destinationCompany = destinationCompanyTextField.getText();
        if(anyMovieWithThisTitle(title)){
            if (destinationCompany != null){
                showTransferConfirmationMessage();
            }
            else companyNoticeLabel.setText("Destination Company must be filled");
        }
        else
            titleNoticeLabel.setText("No movie with this title");
    }
    @FXML
    void backToHome(){
        try {
            client.showCompanyHome();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    boolean anyMovieWithThisTitle(String title){
        boolean movieExists = false;
        for (Movie movie : ClientMain.companyMovie){
            if(title.equalsIgnoreCase(movie.getTitle())){
                movieExists = true;
                break;
            }
        }
        return movieExists;
    }
    void showTransferConfirmationMessage(){
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TransferConfirmationMessage.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        TransferConfirmationMessageController transferConfirmationMessageController = fxmlLoader.getController();
        transferConfirmationMessageController.setClient(client);
        transferConfirmationMessageController.setTitle(title);
        transferConfirmationMessageController.setDestinationCompany(destinationCompany);
        transferConfirmationMessageController.messageLabel.setText("ARE YOU CONFIRM?");
        stage.setTitle("CONFIRMATION");
        stage.setScene(scene);
        stage.show();
        transferConfirmationMessageController.setStage(stage);
    }
}
