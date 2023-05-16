package client.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddMoviePageController {
    ClientMain client;
    @FXML
    TextField titleTextField, releaseYearTextField, genre1TextField, genre2TextField, genre3TextField, runningTimeTextField, budgetTextField, revenueTextField;
    @FXML
    Label noticeLabel;
    Movie movie;
    void setClient(ClientMain client){
        this.client = client;
    }
    @FXML
    void addMovieRequest(){
        String[] movieAttribute =new String[9];
        movieAttribute[0] = titleTextField.getText();
        movieAttribute[1] = releaseYearTextField.getText();
        movieAttribute[2] = genre1TextField.getText();
        movieAttribute[3] = genre2TextField.getText();
        movieAttribute[4] = genre3TextField.getText();
        movieAttribute[5] = runningTimeTextField.getText();
        movieAttribute[6] = ClientMain.companyName;
        movieAttribute[7] = budgetTextField.getText();
        movieAttribute[8] = revenueTextField.getText();

        if (movieAttribute[0] != null){
            if (!movieAlreadyAdded(movieAttribute[0])){
                if (movieAttribute[1] != null && (movieAttribute[2] != null || movieAttribute[3] != null || movieAttribute[4] != null) && movieAttribute[5] != null && movieAttribute[6] != null && movieAttribute[7] != null && movieAttribute[8] != null){
                    try {
                        Integer.parseInt(movieAttribute[1]);
                        Integer.parseInt(movieAttribute[5]);
                        Long.parseLong(movieAttribute[7]);
                        Long.parseLong(movieAttribute[8]);
                        movie = new Movie(movieAttribute);
                        showAdditionConfirmationMessage();
                    }catch (NumberFormatException exception){
                        noticeLabel.setText("Release year, Running time, Budget and Revenue Must be a number");
                    }
                }
                else
                    noticeLabel.setText("All the attribute must be filled");
            }
            else
                noticeLabel.setText("The movie is already added");
        }
        else
            noticeLabel.setText("Title cannot be empty");
    }
    @FXML
    void backToHome(){
        try {
            client.showCompanyHome();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    boolean movieAlreadyAdded(String title){
        boolean movieAlreadyAdded = false;
        for (Movie movie : ClientMain.companyMovie){
            if (title.equalsIgnoreCase(movie.getTitle())){
                movieAlreadyAdded = true;
                break;
            }
        }
        return movieAlreadyAdded;
    }
    void showAdditionConfirmationMessage(){
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdditionConfirmationMessage.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        AdditionConfirmationMessageController additionConfirmationMessageController = fxmlLoader.getController();
        additionConfirmationMessageController.setClient(client);
        additionConfirmationMessageController.setMovie(movie);
        additionConfirmationMessageController.confirmationLabel.setText("ARE YOU CONFIRM?");
        stage.setTitle("CONFIRMATION");
        stage.setScene(scene);
        stage.show();
        additionConfirmationMessageController.setStage(stage);
    }
}
