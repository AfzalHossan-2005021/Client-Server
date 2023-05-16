package client.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

import static client.project.ClientMain.companyMovie;

public class LoginController {
    ClientMain client;
    @FXML
    TextField companyNameTextField, passwordTextField;
    @FXML
    Label notRegisteredNotification;
    @FXML
    Button loginButton;
    void setClient(ClientMain client){
        this.client = client;
    }
    @FXML
    void loginCheck() throws IOException {
        ClientMain.companyName = companyNameTextField.getText();
        try {
            ClientMain.socketWrapper.write(ClientMain.companyName);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        String messageFromServer = null;
        try {
            messageFromServer = (String) ClientMain.socketWrapper.read();
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        assert messageFromServer != null;
        if (messageFromServer.equals("No objection")){
            companyNameTextField.clear();
            passwordTextField.clear();
            try {
                ClientMain.companyMovie = (ArrayList<Movie>)ClientMain.socketWrapper.read();
            } catch (ClassNotFoundException exception) {
                exception.printStackTrace();
            }
            ClientMain.allMovies.setAll(companyMovie);
            ClientMain.recentMovies.setAll(getMostRecentMovies());
            ClientMain.maxRevenueMovie.setAll(getMoviesWithMaxRevenue());
            ClientMain.readThread = new ReadThread();
            client.showCompanyHome();
        }
        else{
            notRegisteredNotification.setText(messageFromServer);
            companyNameTextField.clear();
            passwordTextField.clear();
        }
    }
    public int getMostRecentYear(){
        int recentYear = 0;
        for (Movie movie: companyMovie)
            if(movie.getYearOfRelease() > recentYear)
                recentYear = movie.getYearOfRelease();
        return recentYear;
    }
    public ArrayList<Movie> getMostRecentMovies(){
        ArrayList<Movie> mostRecentMovies = new ArrayList<>();
        int recentYear = getMostRecentYear();
        for (Movie movie: companyMovie)
            if (movie.getYearOfRelease() == recentYear)
                mostRecentMovies.add(movie);
        return mostRecentMovies;
    }
    public long getMaxRevenue() {
        long maxRevenue = 0;
        for (Movie movie: companyMovie)
            if(maxRevenue < movie.getRevenue())
                maxRevenue = movie.getRevenue();
        return maxRevenue;
    }
    public ArrayList<Movie> getMoviesWithMaxRevenue() {
        long maxRevenue = getMaxRevenue();
        ArrayList<Movie> moviesWithMaxRevenue = new ArrayList<>();
        for (Movie movie : companyMovie)
            if (movie.getRevenue() == maxRevenue)
                moviesWithMaxRevenue.add(movie);
        return moviesWithMaxRevenue;
    }
}
