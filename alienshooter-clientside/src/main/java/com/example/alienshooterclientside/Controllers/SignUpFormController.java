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
 * This SignUpFormController is used to control the actions taken on the
 * sign up form like clicking a button.
 *
 * The sign up form is created using Scene builder, it gave a
 * fxml file signUpForm_final.fxml which is stored under directory resources/FXMLFiles
 *
 * This fxml file is binded with this class by adding fx:controller: "..."
 * to the fxml file.
 *
 * After this attributed added, we also bind the attributes of the class and the
 * buttons, textfields passwordfield.
 *
 * Depending on these buttons and fields we take an action.
 *
 * We also bind the functions with onAction attribute.
 */
public class SignUpFormController {
    @FXML
    private TextField textUsername;

    @FXML
    private TextField textEmail;

    @FXML
    private PasswordField textPassword;

    @FXML
    private PasswordField textConfirm;

    @FXML
    private Button buttonSignUp;

    @FXML
    private Button buttonGoBack;

    /**
     * This function is called when the player clicks on the Sign up button
     *
     * Firstly it checks if there is any unfilled text or password field, if so
     * the player is warned showing a new screen and warning message
     *
     * Then it creates a new player based on the entries on the username and password
     * fiels.
     *
     * The player is sent to the REST service in a post method and according to the return
     * coming from the service we take an action.
     *
     * if the player username is already in use, we stay in the same page and
     * the player again need to fill all the gaps.
     *
     * if the player username is unique the player is saved into the database and
     * the player is directed to the sign in page.
     *
     * @param event
     */
    @FXML
    public void handleSignUpButtonAction(ActionEvent event) {
        Window owner = buttonSignUp.getScene().getWindow();
        if(textUsername.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a username.");
            return;
        }

        if(textEmail.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter an E-mail.");
            return;
        }

        if(textPassword.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password.");
            return;
        }

        if(textConfirm.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter the confirmation password.");
            return;
        }

        if (!textConfirm.getText().equals(textPassword.getText())) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "The password that you've entered does not match the confirmation password.");
            return;
        }


        String register;
        RestConsumer restConsumer = new RestConsumer();
        Player player = new Player(textUsername.getText(), textPassword.getText());
        register = restConsumer.registerPlayer(player);
        if (register.equals("Player Saved.")) {
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            try {
                Parent signIn = FXMLLoader.load(getClass().getResource("/FXMLFiles/signInForm_final.fxml"));
                Scene signUpScene = new Scene(signIn, 1200, 720);
                primaryStage.setScene(signUpScene);
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (register.equals("Nickname Not Unique.")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "The username that you've entered is already taken. Please choose another one.");
        }
        else {
            System.out.println("Can not connect to the server.");
        }
    }

    /**
     * This function is called when the player clicks on the Go Back button
     *
     * This function takes the player from the sign up form to the sign in form.
     *
     * @param actionEvent
     */
    public void handleGoBackButtonAction(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        try {
            Parent signIn = FXMLLoader.load(getClass().getResource("/FXMLFiles/signInForm_final.fxml"));
            Scene signUpScene = new Scene(signIn, 1200, 720);
            primaryStage.setScene(signUpScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }}