package client.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AdditionConfirmationMessageController {
    ClientMain client;
    Stage stage;
    Movie movie;
    @FXML
    public Label confirmationLabel;
    @FXML
    void setClient(ClientMain client){
        this.client = client;
    }
    void setStage(Stage stage){
        this.stage = stage;
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    @FXML
    void showAdditionSuccessfulMessage(){
        this.stage.close();
        try {
            ClientMain.socketWrapper.write("Add movie");
            ClientMain.socketWrapper.write(movie);
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
        successfulMessageController.messageLabel.setText("MOVIE ADDED SUCCESSFULLY");
        stage.setScene(scene);
        stage.show();
        successfulMessageController.setStage(stage);
    }
    @FXML
    void back(){
        stage.close();
    }
}
