package com.example.alienshooterclientside.Controllers;

import com.example.alienshooterclientside.Entities.Player;
import com.example.alienshooterclientside.Utilities.AlertHelper;
import com.example.alienshooterclientside.Utilities.RestConsumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInFormController {
    private static String username;
    private static String password;

    @FXML
    private TextField textUsername;

    @FXML
    private PasswordField textPassword;

    @FXML
    private Button buttonSignIn;

    @FXML
    private Button buttonSignUp;

    @FXML
    private Button buttonQuit;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void handleSignInButtonAction(ActionEvent actionEvent) {
        Window owner = buttonSignIn.getScene().getWindow();
        if(textUsername.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your name.");
            return;
        }

        if(textPassword.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password.");
            return;
        }
        String login;
        RestConsumer restConsumer = new RestConsumer();
        Player player = new Player(textUsername.getText(), textPassword.getText());
        login = restConsumer.logInPlayer(player);
        if (login.equals("No Such a Player")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "The username that you've entered does not match any account.");
        }
        else {
            long playerId = restConsumer.getPlayerId(textUsername.getText());
            player = new Player(playerId, textUsername.getText(), textPassword.getText());
            login = restConsumer.logInPlayer(player);
            if (login.equals("Logged in.")) {
                System.out.println("logged in.");
                username = textUsername.getText();
                password = textPassword.getText();
                Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                try {
                    Parent mainMenu = FXMLLoader.load(getClass().getResource("/FXMLFiles/mainMenu_final.fxml"));
                    Scene mainMenuScene = new Scene(mainMenu, 1200, 720);
                    primaryStage.setScene(mainMenuScene);
                    primaryStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //navigate to main menu
            } else if (login.equals("Wrong Password.")) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                        "The password that you've entered is incorrect.");
            }
        }
    }

    public void handleSignUpButtonAction(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        try {
            Parent signUp = FXMLLoader.load(getClass().getResource("/FXMLFiles/signUpForm_final.fxml"));
            Scene signUpScene = new Scene(signUp, 1200, 720);
            primaryStage.setScene(signUpScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleQuitButtonAction(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.close();
    }
}
