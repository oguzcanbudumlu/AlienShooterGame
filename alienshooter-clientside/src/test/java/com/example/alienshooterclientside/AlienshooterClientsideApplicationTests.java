package com.example.alienshooterclientside;

import com.example.alienshooterclientside.entities.Game;
import com.example.alienshooterclientside.entities.Player;
import com.example.alienshooterclientside.entities.Score;
import com.example.alienshooterclientside.utilities.RestConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

/**
 * TO RUN THE TESTS CORRECTLY, THE SERVER SHOULD BE RUNNING,
 * AND SHOULD BE RESTARTED.
 *
 ***********************************************************
 *********************RESTART THE SERVER********************
 ***********************************************************
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AlienshooterClientsideApplicationTests {
	RestConsumer restConsumer = new RestConsumer();
	Player player0 = new Player(0, "omer", "cetin");
	Player player1 = new Player(1, "omer_", "cetin_");
	Player player2 = new Player(2, "omer*", "cetin*");
	Game game0 = new Game((long) 0, (long) 10, LocalDate.now(), player0);
	Game game1 = new Game((long) 1, (long) 20, LocalDate.now(), player0);
	List<Score> scoreBoard = new ArrayList<>();

	@Test
	public void resgisterSaved() {
		String register = restConsumer.registerPlayer(player0);
		assertEquals("Player Saved.", register);
	}

	@Test
	public void registerNotUnique() {
		String register = restConsumer.registerPlayer(player0);
		assertEquals("Nickname Not Unique.", register);
	}

	@Test
	public void loginLogged() {
		String login = restConsumer.logInPlayer(player0);
		assertEquals("Logged in.", login);
	}

	@Test
	public void loginNoSuchPlayer() {
		String login = restConsumer.logInPlayer(player1);
		assertEquals("No Such a Player.", login);
	}

	@Test
	public void loginWrongPassword() {
		restConsumer.registerPlayer(player2);
		Player player3 = new Player(2, "omer*", "ctn*");
		String login = restConsumer.logInPlayer(player3);
		assertEquals("Wrong Password.", login);
	}

	@Test
	public void getPlayerId() {
		long playerId = restConsumer.getPlayerId("omer");
		assertEquals(1, playerId);
	}

	@Test
	public void getNextGameId() {
		long gameId = restConsumer.getNextGameId();
		assertEquals(1, gameId);
	}

	/*
	@Test
	public void getScoreBoardAlltheTime() {
		scoreBoard.add(new Score(1, "omer", 10));
		assertEquals(scoreBoard, restConsumer.getScoreBoardAlltheTime());
	}

	@Test
	public void getScoreBoardWeekly() {
		scoreBoard.add(new Score(1,"omer", 10));
		restConsumer.addGame(game0);
		assertEquals(scoreBoard, restConsumer.getScoreBoardWeekly());
	}
	*/
}
