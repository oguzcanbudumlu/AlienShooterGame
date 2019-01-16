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

/**
 * This class implements the Initalizable interface because when
 * the player comes to the page, the tables should be ready in advance
 * initialize function of the interface is used for filling the tables.
 *
 * This LeaderboardsTablesController is the controller of the
 * leaderboards tables page. It is binded with leaderboardsTables_final.fxml
 *
 * The fxml file is prepared with using Scene Builder
 *
 * The leaderboards tables page contains two tables and one go back button to go back
 * to the main menu.
 *
 * one of the tables for the weekly scores and the other one for all the time scores
 *
 * The tables of the class and the fxml files are binded with @FXML annotation.
 *
 * The table colums are filled in initialize function.
 */
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

    /**
     * This function is called, when the Go Back button is clicked
     *
     * After it is clicked, the player is navigated to the main menu.
     *
     * @param actionEvent
     */
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

    /**
     * This initalize function fills the colums of the weekly and all the time
     * score tables.
     *
     * @param url
     * @param resourceBundle
     */
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

    /**
     * This function is used to get the weekly scores from the database
     *
     * It returs an ObservableList<Score> which will be used to fill the colums of
     * the weekly scores table.
     *
     * @return ObservableList<Score> weekly scores
     */
    public ObservableList<Score> getScoreListWeekly() {
        RestConsumer restConsumer = new RestConsumer();
        List<Score> scoreListAllTime = restConsumer.getScoreBoardWeekly();
        ObservableList<Score> weeklyScores = FXCollections.observableArrayList();
        for (Score score : scoreListAllTime) {
            weeklyScores.add(score);
        }
        return weeklyScores;
    }

    /**
     * This function is used to get the all the time scores from the database
     *
     * It returs an ObservableList<Score> which will be used to fill the colums of
     * the all the time scores table.
     *
     * @return ObservableList<Score> weekly scores
     */
    public ObservableList<Score> getScoreListAllTime() {
        RestConsumer restConsumer = new RestConsumer();
        List<Score> scoreListAllTime = restConsumer.getScoreBoardAlltheTime();
        ObservableList<Score> allTimeScores = FXCollections.observableArrayList();
        for (Score score : scoreListAllTime) {
            allTimeScores.add(score);
        }
        return allTimeScores;
    }
}