package client.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class CompanyHomeController {
    ClientMain client;
    @FXML
    Label allMoviesLabel, recentMoviesLabel, maxRevenueMovieLabel, totalProfitLabel, addMovieLabel, transferMovieLabel, logOutLabel;

    void setClient(ClientMain client){
        this.client = client;
    }
    @FXML
    void showAllMovies(){
        try {
            client.showAllMoviesPage();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    @FXML
    void showRecentMovies(){
        try {
            client.showRecentMoviePage();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    @FXML
    void showMaxRevenueMovie(){
        try {
            client.showMaxRevenueMoviePage();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    @FXML
    void showTotalProfit(){
        try {
            client.showTotalProfitPage();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    @FXML
    void addMovie(){
        try{
            client.showAddMoviePage();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    @FXML
    void transferMovie(){
        try {
            client.showTransferMovie();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    @FXML
    void logOut(){
        showLogoutConfirmationMessage();
    }
    void showLogoutConfirmationMessage() {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LogoutConfirmation.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        LogoutConfirmationController logoutConfirmationController = fxmlLoader.getController();
        logoutConfirmationController.setClient(client);
        logoutConfirmationController.messageLabel.setText("ARE YOU CONFIRM?");
        stage.setTitle("CONFIRMATION");
        stage.setScene(scene);
        stage.show();
        logoutConfirmationController.setStage(stage);
    }
}
