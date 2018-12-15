package com.example.alienshooterclientside.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    @FXML
    private Button buttonStartGame;

    @FXML
    private Button buttonLeaderboards;

    @FXML
    private Button buttonQuit;

    public void handleStartGameButtonAction(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        GameController gameController = new GameController(primaryStage);
        gameController.start();
    }

    public void handleLeaderboardsButtonAction(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        try {
            Parent leaderboardsTables = FXMLLoader.load(getClass().getResource("/FXMLFiles/leaderboardsTables_final.fxml"));
            Scene leaderboardsTablesScene = new Scene(leaderboardsTables, 1200, 720);
            primaryStage.setScene(leaderboardsTablesScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleQuitButtonAction(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.close();
    }

    public void handleSignOutButtonAction(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        try {
            Parent signIn = FXMLLoader.load(getClass().getResource("/FXMLFiles/signInForm_final.fxml"));
            Scene signUpScene = new Scene(signIn, 1200, 720);
            primaryStage.setScene(signUpScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
