package com.example.alienshooterclientside;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * This is the main class where everything starts
 */
@SpringBootApplication
public class AlienshooterClientsideApplication extends Application {

	private ConfigurableApplicationContext springContext;


	@Override
	public void init() throws Exception {
		springContext = SpringApplication.run(AlienshooterClientsideApplication.class);
	}

	/**
	 * This function is called after the init() function.
	 * The primaryStage is the first screen that is seen by the player.
	 * The first screen is sign in form.
	 * The title of the first screen is "Alien Shooter"
	 * We set its resizable property as false so the size of the screen can not be changed.
	 * At the end we send the screen.
	 * @param primaryStage
	 * @throws Exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Alien Shooter");
		Parent signIn = FXMLLoader.load(getClass().getResource("/FXMLFiles/signInForm_final.fxml"));
		Scene signInScene = new Scene(signIn, 1200, 720);
		primaryStage.setScene(signInScene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		springContext.stop();
	}


	public static void main(String[] args) {
		launch(AlienshooterClientsideApplication.class, args);
	}
}