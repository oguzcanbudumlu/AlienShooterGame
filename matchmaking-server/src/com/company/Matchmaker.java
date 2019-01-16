package com.company;

import com.company.entities.ServerMessage;
import com.company.entities.Session;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


import static com.company.utilities.Constants.*;


/**
 * In Matchmaker, players waiting for
 * 4th level are matched. One socket is
 * opened for each pair. Once a player
 * comes to 4th level, a socket is opened
 * for a game session and another player
 * finishing 3th level in game joins this
 * session via same socket. After that,
 * one thread is created to create another
 * two threads.
 */
public class Matchmaker {

    public static void main(String[] args) throws IOException {
        final int port = PORT;
        ServerMessage serverMessage;

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server has been started at port " + port + ".");

        while (true) {
            System.out.println("Waiting for the players..");

            Socket player1 = serverSocket.accept();
            System.out.println("Player1 has joined the game.");

            serverMessage = new ServerMessage(PLAYER1_JOINED, INIT_POSITION, INIT_HEALTH, INIT_SHOT, INIT_WON);
            String messagePlayer1 = objectWriter.writeValueAsString(serverMessage);
            serverMessage = new ServerMessage(PLAYER2_JOINED, INIT_POSITION, INIT_HEALTH, INIT_SHOT, INIT_WON);
            String messagePlayer2 = objectWriter.writeValueAsString(serverMessage);

            Socket player2 = serverSocket.accept();
            System.out.println("Player2 has joined the game.");

            new DataOutputStream(player1.getOutputStream()).writeUTF(messagePlayer1);
            new DataOutputStream(player2.getOutputStream()).writeUTF(messagePlayer2);

            new Thread(new Session(player1, player2)).start();
        }
    }
}
