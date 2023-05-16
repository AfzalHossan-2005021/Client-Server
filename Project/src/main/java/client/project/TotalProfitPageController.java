package client.project;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class TotalProfitPageController {
    ClientMain client;
    @FXML
    TextArea amountTextArea;
    void setClient(ClientMain client){
        this.client = client;
    }
    void setTotalProfit(){
        Long totalProfit = 0L;
        for (Movie movie : ClientMain.recentMovies)
            totalProfit += movie.getProfit();
        amountTextArea.setText(String.valueOf(totalProfit));
    }
    @FXML
    void backToHome(){
        try {
            client.showCompanyHome();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
