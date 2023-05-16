package client.project;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AllMoviesPageController implements Initializable {
    ClientMain client;
    @FXML
    TableView<Movie> table;
    @FXML
    TableColumn<Movie, String> titleColumn;
    @FXML
    TableColumn<Movie, Integer> releaseYearColumn;
    @FXML
    TableColumn<Movie, String> genreColumn;
    @FXML
    TableColumn<Movie, Integer> runningTimeColumn;
    @FXML
    TableColumn<Movie, Long> budgetColumn;
    @FXML
    TableColumn<Movie, Long> revenueColumn;
    void setClient(ClientMain client){
        this.client = client;
    }
    public void setTable(ObservableList<Movie> movieList){
        table.setItems(movieList);
        table.setFixedCellSize(25);
        table.setMaxHeight(movieList.size() * table.getFixedCellSize() + 30);
    }
    @FXML
    void backToHome(){
        try {
            client.showCompanyHome();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setEditable(true);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("YearOfRelease"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("AllGenre"));
        runningTimeColumn.setCellValueFactory(new PropertyValueFactory<>("RunningTime"));
        budgetColumn.setCellValueFactory(new PropertyValueFactory<>("Budget"));
        revenueColumn.setCellValueFactory(new PropertyValueFactory<>("Revenue"));
    }

}
