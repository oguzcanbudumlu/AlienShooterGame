package com.example.alienshooterclientside.Controllers;

import com.example.alienshooterclientside.Entities.Game;
import com.example.alienshooterclientside.Entities.Player;
import com.example.alienshooterclientside.Utilities.RestConsumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;

public class GameOverController {
    Stage stage;

    @FXML
    private Button buttonMainMenu;

    @FXML
    private Button buttonNewGame;

    @FXML
    private  Button buttonQuit;

    private Label labelScore = new Label();

    @FXML
    public void start(Stage stage, int score) {
        writeScoreToDatabase(score);
        labelScore.setText(score + "");
        labelScore.setLayoutX(705);
        labelScore.setLayoutY(360);
        labelScore.setTextFill(Color.WHITE);
        labelScore.setFont(Font.font("Cousine Bold", 150));
        try {
            Parent gameOver = FXMLLoader.load(getClass().getResource("/FXMLFiles/gameOverLayout_final.fxml"));
            Pane root = (Pane) gameOver;
            root.getChildren().add(labelScore);
            Scene gameOverScene = new Scene(root, 1200, 720);
            stage.setScene(gameOverScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeScoreToDatabase(int score) {
        Game game;
        Player player;
        long playerId, gameId;
        String username, password;
        SignInFormController signInFormController = new SignInFormController();
        username = signInFormController.getUsername();
        password = signInFormController.getPassword();

        RestConsumer restConsumer = new RestConsumer();
        playerId = restConsumer.getPlayerId(username);
        gameId = restConsumer.getNextGameId();

        player = new Player(playerId, username, password);

        game = new Game(gameId, (long) score, LocalDate.now(), player);

        restConsumer.addGame(game);
    }

    public void handleMainMenuButtonAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        try {
            Parent mainMenu = FXMLLoader.load(getClass().getResource("/FXMLFiles/mainMenu_final.fxml"));
            Scene mainMenuScene = new Scene(mainMenu, 1200, 720);
            stage.setScene(mainMenuScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleNewGameButtonAction(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        GameController gameController = new GameController(primaryStage);
        gameController.start();
    }


    public void handleQuitButtonAction(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.close();
    }
}
























