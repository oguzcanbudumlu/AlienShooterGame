package com.example.alienshooterclientside.Controllers;

import com.example.alienshooterclientside.Entities.Player;
import com.example.alienshooterclientside.Utilities.AlertHelper;
import com.example.alienshooterclientside.Utilities.RestConsumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.IOException;

/**
 * This SignInFormController is used to control the actions taken on the
 * sign in form like clicking a button.
 *
 * The sign in form is created using Scene Builder.
 * Sceen Builder gives a FXML file after the form is created.
 *
 * The fmxl file is binded with this class with adding
 * fx:controller="com.example.alienshooterclientside.Controllers.SignInFormController" to the fxml file
 *
 * After this fx:controller attribute is added to the fxml file we can bind the buttons
 * textfields in the fxml file with the class attributes.
 *
 * We also bind the button actions with functions.
 *
 * This class also contains two String attributes username and pass word,
 * These two attribute are used when we add a new game to the database.
 * These attributes are set when the player logs in.
 * These basically indicate who logs in and plays.
 */
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

    //used to get the username from another class
    public String getUsername() {
        return username;
    }

    //used to get the password from another class
    public String getPassword() {
        return password;
    }

    /**
     * This function is called when the player clicks on the Sign In button.
     *
     * It checks if there is and unfilled text field if so, it warns the player
     * creating a new window and showing the warning message.
     *
     * If username and password are entered then it sends a post request using RestConsumer
     * class to login
     *
     * According to the return value of the request, player logs in and goes to main menu
     *
     * If the password is wrong or username is not in the database the player is warned
     *
     * @param actionEvent
     */
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
        if (login.equals("No Such a Player.")) {
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

    /**
     * This function is called when sign up button is clicked,
     *
     * It takes the player from sign in form to sign up form
     *
     * It opens Sing Up form page
     *
     * @param actionEvent
     */
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

    /**
     * When Quit button is clicked this function is called
     *
     * The app is closed
     *
     * @param actionEvent
     */
    public void handleQuitButtonAction(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.close();
    }
}