package com.example.alienshooterclientside.Controllers;

import com.example.alienshooterclientside.Entities.Score;
import com.example.alienshooterclientside.Utilities.RestConsumer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LeaderboardsTablesController implements Initializable {
    @FXML
    private TableView<Score> tableWeekly;

    @FXML
    private TableView<Score> tableAllTime;

    @FXML
    private TableColumn<Score, Integer> columnRankingW;

    @FXML
    private TableColumn<Score, Integer> columnRankingA;

    @FXML
    private TableColumn<Score, String> columnUsernameW;

    @FXML
    private TableColumn<Score, String> columnUsernameA;

    @FXML
    private TableColumn<Score, Long> columnScoreW;

    @FXML
    private TableColumn<Score, Long> columnScoreA;

    @FXML
    private Button buttonGoBack;

    public void handleGoBackButtonAction(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        try {
            Parent mainMenu = FXMLLoader.load(getClass().getResource("/FXMLFiles/mainMenu_final.fxml"));
            Scene mainMenuScene = new Scene(mainMenu, 1200, 720);
            primaryStage.setScene(mainMenuScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnRankingA.setCellValueFactory(new PropertyValueFactory<>("ranking"));
        columnUsernameA.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnScoreA.setCellValueFactory(new PropertyValueFactory<>("score"));
        tableAllTime.setItems(getScoreListAllTime());

        columnRankingW.setCellValueFactory(new PropertyValueFactory<>("ranking"));
        columnUsernameW.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnScoreW.setCellValueFactory(new PropertyValueFactory<>("score"));
        tableWeekly.setItems(getScoreListWeekly());
    }

    public ObservableList<Score> getScoreListWeekly() {
        RestConsumer restConsumer = new RestConsumer();
        List<Score> scoreListAllTime = restConsumer.getScoreBoardWeekly();
        ObservableList<Score> weeklyScores = FXCollections.observableArrayList();
        for (Score score : scoreListAllTime) {
            System.out.println("here");
            weeklyScores.add(score);
        }
        return weeklyScores;
    }

    public ObservableList<Score> getScoreListAllTime() {
        RestConsumer restConsumer = new RestConsumer();
        List<Score> scoreListAllTime = restConsumer.getScoreBoardAlltheTime();
        ObservableList<Score> allTimeScores = FXCollections.observableArrayList();
        for (Score score : scoreListAllTime) {
            System.out.println("here");
            allTimeScores.add(score);
        }
        return allTimeScores;
    }
}
