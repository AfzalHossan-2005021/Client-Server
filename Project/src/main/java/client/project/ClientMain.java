package client.project;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientMain extends Application {
    final String serverAddress = "127.0.0.1";
    final int serverPort = 12345;
    Stage stage;
    public static SocketWrapper socketWrapper;
    public static ArrayList<Movie> companyMovie;
    public static String companyName;
    public static ReadThread readThread;
    public static boolean runReadThread = true;
    public static ObservableList<Movie> allMovies;
    public static ObservableList<Movie> recentMovies;
    public static ObservableList<Movie> maxRevenueMovie;

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        showLoginPage();
    }

    void showLoginPage() throws IOException {
        allMovies = FXCollections.observableArrayList();
        recentMovies = FXCollections.observableArrayList();
        maxRevenueMovie = FXCollections.observableArrayList();
        runReadThread = true;
        socketWrapper = new SocketWrapper(new Socket(serverAddress, serverPort));
        FXMLLoader fxmlLoader = new FXMLLoader(ClientMain.class.getResource("LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LoginController loginController = fxmlLoader.getController();
        loginController.setClient(this);
        stage.setTitle("LOGIN");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> close(stage));
    }

    void showCompanyHome() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientMain.class.getResource("CompanyHome.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        CompanyHomeController companyHomeController = fxmlLoader.getController();
        companyHomeController.setClient(this);
        stage.setTitle("HOME");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event ->{
            event.consume();
            logOut();
                }
        );
    }

    void showAllMoviesPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientMain.class.getResource("AllMoviesPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AllMoviesPageController showAllMoviesPageController = fxmlLoader.getController();
        showAllMoviesPageController.setClient(this);
        showAllMoviesPageController.setTable(allMovies);
        stage.setTitle("ALL MOVIES");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event ->{
                    event.consume();
                    logOut();
                }
        );
    }

    void showMaxRevenueMoviePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientMain.class.getResource("MaxRevenueMoviePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MaxRevenueMoviePageController maxRevenueMoviePageController = fxmlLoader.getController();
        maxRevenueMoviePageController.setClient(this);
        maxRevenueMoviePageController.setTable(maxRevenueMovie);
        stage.setTitle("MOVIES WITH MAXIMUM REVENUE");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event ->{
                    event.consume();
                    logOut();
                }
        );
    }

    void showRecentMoviePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientMain.class.getResource("RecentMoviePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        RecentMoviePageController recentMoviePageController = fxmlLoader.getController();
        recentMoviePageController.setClient(this);
        recentMoviePageController.setTable(recentMovies);
        stage.setTitle("MOST RECENT MOVIES");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event ->{
                    event.consume();
                    logOut();
                }
        );
    }

    void showTotalProfitPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientMain.class.getResource("TotalProfitPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        TotalProfitPageController totalProfitPageController = fxmlLoader.getController();
        totalProfitPageController.setClient(this);
        totalProfitPageController.setTotalProfit();
        stage.setTitle("TOTAL PROFIT");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event ->{
                    event.consume();
                    logOut();
                }
        );
    }

    void showAddMoviePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientMain.class.getResource("AddMoviePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AddMoviePageController addMoviePageController = fxmlLoader.getController();
        addMoviePageController.setClient(this);
        stage.setTitle("ADD MOVIE");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event ->{
                    event.consume();
                    logOut();
                }
        );
    }

    void showTransferMovie() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientMain.class.getResource("TransferMoviePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        TransferMoviePageController transferMoviePageController = fxmlLoader.getController();
        transferMoviePageController.setClient(this);
        stage.setTitle("TRANSFER MOVIE");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event ->{
                    event.consume();
                    logOut();
                }
        );
    }
    public void close(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("You are about to close");
        alert.setContentText("Are you confirm?");
        if (alert.showAndWait().get() == ButtonType.YES){
            stage.close();
            try {
                ClientMain.socketWrapper.write("Close");
                ClientMain.socketWrapper.closeConnection();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
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
        logoutConfirmationController.setClient(this);
        logoutConfirmationController.messageLabel.setText("ARE YOU CONFIRM?");
        stage.setTitle("LOGOUT CONFIRMATION");
        stage.setScene(scene);
        stage.show();
        logoutConfirmationController.setStage(stage);
    }
}