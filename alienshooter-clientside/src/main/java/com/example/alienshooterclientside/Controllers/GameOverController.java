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
import java.io.IOException;
import java.time.LocalDate;

/**
 * This class is used to control the game over page.
 *
 * The game over page is created using Scene Builder. It gave us a
 * fxml file.
 *
 * This fxml file is binded with the GameOverController via the attribute
 * fx:contoller = "..."
 *
 * The game over page contains 3 buttons and one label which show the score
 * after the game ends.
 *
 * The actions of these 3 buttons are controlled by the functions of this class.
 */
public class GameOverController {
    //used to change the scene of the current stage.
    Stage stage;

    @FXML
    private Button buttonMainMenu;

    @FXML
    private Button buttonNewGame;

    @FXML
    private  Button buttonQuit;

    //used to show the score
    private Label labelScore = new Label();

    /**
     * this funcion takes two parameters stage and score
     *
     * stage is used to change the current scene of the stage and score
     * is used the show the score of the player on the page.
     *
     * it call writeScoreToDAtabase(score) function to write the score to the database.
     *
     * then it puts the score on the page and change the scene of the stage.
     *
     * @param stage
     * @param score
     */
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

    /**
     * this function used to save the game with score and player to the database
     *
     * it creates a game with the score and the player retrieved form the signincontroller
     *
     * then it calls the addGame function from the RestConsumer class
     * @param score
     */
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

    /**
     * when the main menu button is clicked this function is called
     *
     * the player is navigated to the main menu
     *
     * @param actionEvent
     */
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

    /**
     * when the new game button is clicked this function is called.
     *
     * the player starts to play a new game.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void handleNewGameButtonAction(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        GameController gameController = new GameController(primaryStage);
        gameController.start();
    }

    /**
     * when the quit button is clicked, this function is called
     *
     * the app is closed with this function.
     * @param actionEvent
     */
    public void handleQuitButtonAction(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.close();
    }
}