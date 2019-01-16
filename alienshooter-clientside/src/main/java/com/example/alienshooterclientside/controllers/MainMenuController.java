package com.example.alienshooterclientside.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * This MainMenuController is used to control the button actions on
 * the main menu.
 *
 * The main menu page is created using Scene Builder, it gave a fxml file
 * mainMenu_final.fxml which is stored under the directory resources/FXMLFiles
 *
 * This fxml file is binded with this class Main Menu Controller by adding fx:controller: "..."
 * to the fxml file.
 *
 * The function of this class is binded with the buttons on the main menu page with
 * onAction attribute in the fxml file.
 *
 * On main menu page, we can navigate to start a new game, show the scoreboards and
 * sing in page and we can quit the app. All these actions are done with the help of
 * functions of this class.
 */
public class MainMenuController {
    @FXML
    private Button buttonStartGame;

    @FXML
    private Button buttonLeaderboards;

    @FXML
    private Button buttonSignOut;

    @FXML
    private Button buttonQuit;

    /**
     * When the player clicks on the Start Game button, this function is called
     *
     * It starts a new game.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void handleStartGameButtonAction(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        GameController gameController = new GameController(primaryStage);
        gameController.start();
    }

    /**
     * This function is called when the Leaderships button is clicked.
     *
     * The player is navigated to the Leaderships Page.
     *
     * @param actionEvent
     */
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

    /**
     * This function is called when Quit button is clicked.
     *
     * The app is closed.
     *
     * @param actionEvent
     */
    public void handleQuitButtonAction(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.close();
    }

    /**
     * This function is called when the player clicks on the Sign Out button.
     *
     * The player is navigated to the sign in page.
     *
     * @param actionEvent
     */
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
