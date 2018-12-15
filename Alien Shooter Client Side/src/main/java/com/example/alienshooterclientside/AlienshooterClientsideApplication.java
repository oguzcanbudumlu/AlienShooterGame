package com.example.alienshooterclientside;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AlienshooterClientsideApplication extends Application {

	private ConfigurableApplicationContext springContext;

	@Override
	public void init() throws Exception {
		springContext = SpringApplication.run(AlienshooterClientsideApplication.class);
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Alien Shooter");
		Parent signIn = FXMLLoader.load(getClass().getResource("/FXMLFiles/signInForm_final.fxml"));
		Scene signInScene = new Scene(signIn, 1200, 720);
		primaryStage.setScene(signInScene);
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