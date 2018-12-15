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

public class RestConsumer {
    public RestConsumer() {}

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

    public void getGames() {
        try {
            String address = "http://localhost:8080/games/";
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

            in.close();
            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
