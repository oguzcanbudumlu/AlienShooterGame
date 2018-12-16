package com.example.alienshooterclientside.Utilities;

import com.example.alienshooterclientside.Entities.Game;
import com.example.alienshooterclientside.Entities.Player;
import com.example.alienshooterclientside.Entities.Score;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * The purpose of Restconsumer is to connect to the REST server and
 * consume that REST service.
 *
 * A new player can be registered to the database.
 *
 * A player can be logged in based on the data on the database.
 *
 * A player can get its playerId from the database,
 * this playerId is necessary for recording players scores to the database.
 *
 * A new game score can be registered to the database after a game is played.
 *
 * We can get the next gameId from the database, each game has to have a unique game id,
 * so we send a query to the database to get the next gameId. This is done after a game is played and
 * its score needs to be registered to the database depending on this gameId.
 *
 * We can get the weekly scoreboard and all the time scoreboard.
 *
 * For some debugging purposes we get all the games recorded in the databese.
 */
public class RestConsumer {
    public RestConsumer() {}

    /**
     * This registerPlayer function is used to save a player to the database.
     * It is used in the sign up form in the game.
     * It takes a player as a parameter and sends a post request to the REST service.
     *
     * It checks if the username that is entered, is already in use or not if in use, it returns
     * "Nickname Not Unique." and it does not save the player to the database.
     *
     * If the username is not in use, the player is registered to the database and
     * the function returns "Player Saved."
     *
     * @param player
     * @return String (either "Nickname Not Unique" or "Player Saved.")
     */
    public String registerPlayer(Player player) {
        String output = new String();
        try {
            URL url = new URL("http://localhost:8080/register");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            String input = "{\"nickname\":\"";
            input = input.concat(player.getNickname());
            input = input.concat("\",\"password\":\"");
            input = input.concat(player.getPassword());
            input = input.concat("\"}");

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            String line;
            while ((line = br.readLine()) != null) {
                output = output.concat(line);
            }

            connection.disconnect();

            return output;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * This logInPlayer function is used when a player logs in to the game using his/her username and password.
     * It is used in the sign in form.
     * It takes a player as parameter and sends a post request to the REST service.
     *
     * It checks if the player is registered in the database or not, if it is not registered before,
     * it returns "No such a player.".
     *
     * If there is player with the username that is entered and the password is wrong that
     * it returns "Wrong password.".
     *
     * If the username and the password match a player in the database then
     * it returns "Logged In.".
     *
     * @param player
     * @return String ("No Such a Player.", "Wrong Password." or "Logged In.")
     */
    public String logInPlayer(Player player) {
        String output = new String();
        try {
            URL url = new URL("http://localhost:8080/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            String input = "{\"nickname\":\"";
            input = input.concat(player.getNickname());
            input = input.concat("\",\"password\":\"");
            input = input.concat(Integer.toString(player.getPassword().hashCode()));
            input = input.concat("\"}");

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            String line;
            while ((line = br.readLine()) != null) {
                output = output.concat(line);
            }

            connection.disconnect();

            return output;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * This getPlayerId functions is used when we save a game to the database and a game needs a player,
     * this player needs to contain all the attributes like Player(playerId, username, password).
     * So this is used when we add a new game to the database.
     * It takes a String nickname as a parameter and returns the playerId with this username.
     * Its a get request to the REST service.
     * @param nickname
     * @return the playerId with the nickname
     */
    public long getPlayerId(String nickname) {
        long playerId = -1;
        try {
            String address = "http://localhost:8080/playerid/";
            address = address.concat(nickname);
            URL url = new URL(address);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String line;
            StringBuffer response = new StringBuffer();
            while ((line = in.readLine()) != null) {
                response.append(line);

            }

            in.close();
            connection.disconnect();

            playerId = Long.parseLong(response.toString());
        } catch (MalformedURLException e) {
            System.out.println("abc");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return playerId;
    }

    /**
     * This addGame function is used to save a game to the database.
     * It takes a game object as parametes and it converts this game like String json.
     * Then its sends a post request to the REST service.
     * The game is saved to the database after the request.
     * @param game
     */
    public void addGame(Game game) {
        try {
            URL url = new URL("http://localhost:8080/addgame/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            String input = "{\"gameId\":";
            input = input.concat(Long.toString(getNextGameId()));
            input = input.concat(",\"score\":");
            input = input.concat(game.getScore().toString());
            input = input.concat(",\"date\":\"");
            input = input.concat(game.getDate().toString());
            input = input.concat("\",\"player\":{\"playerId\":");
            input = input.concat(String.valueOf(getPlayerId(game.getPlayer().getNickname())));
            input = input.concat(",\"nickname\":\"");
            input = input.concat(game.getPlayer().getNickname());
            input = input.concat("\",\"password\":\"");
            input = input.concat(Integer.toString(game.getPlayer().getPassword().hashCode()));
            input = input.concat("\"}}");

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            String line;
            String output = new String();
            while ((line = br.readLine()) != null) {
                output = output.concat(line);
            }

            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This getNextGameId function is used to get a new gameId.
     * We need such a function because when we add a new game to the datase,
     * we need a unique gameId. We get it with this function.
     * @return long nextGameId
     */
    public long getNextGameId() {
        long gameId = -1;
        try {
            String address = "http://localhost:8080/getNextGameId/";
            URL url = new URL(address);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String line;
            StringBuffer response = new StringBuffer();
            while ((line = in.readLine()) != null) {
                response.append(line);

            }

            in.close();
            connection.disconnect();

            gameId = Long.parseLong(response.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameId;
    }

    /**
     * This function is required because we need to show the scoreboard to the players
     * In the game when the player enter the score boards page this function is called and
     * its a get request to the server to get all the time scores, based on the return value, the table
     * in the score board screen is filled.
     * @return List<Score> scoreBoardAlltheTime
     */
    public List<Score> getScoreBoardAlltheTime() {
        List<Score> scoreBoard = new ArrayList<>();
        try {
            String address = "http://localhost:8080/scoreboardallthetime/";
            URL url = new URL(address);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String line;
            StringBuffer response = new StringBuffer();
            while ((line = in.readLine()) != null) {
                response.append(line);

            }
            System.out.println(response.toString());

            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Score score = new Score(i + 1, jsonObject.getString("name"), jsonObject.getLong("score"));
                scoreBoard.add(score);
            }

            in.close();
            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return scoreBoard;
    }

    /**
     * This function is required because we need to show the scoreboard to the players
     * In the game when the player enter the score boards page this function is called and
     * its a get request to the server to get the weekly scores, based on the return value, the table
     * in the score board screen is filled.
     * @return List<Score> scoreBoardWeekly
     */
    public List<Score> getScoreBoardWeekly() {
        List<Score> scoreBoard = new ArrayList<>();
        try {
            String address = "http://localhost:8080/scoreboardweekly/";
            URL url = new URL(address);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String line;
            StringBuffer response = new StringBuffer();
            while ((line = in.readLine()) != null) {
                response.append(line);

            }
            System.out.println(response.toString());

            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Score score = new Score(i + 1,jsonObject.getString("name"), jsonObject.getLong("score"));
                scoreBoard.add(score);
            }

            in.close();
            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return scoreBoard;
    }
}