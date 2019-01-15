package com.company;

import com.company.Entities.ServerMessage;
import com.company.Entities.Session;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Matchmaker {

    public static void main(String[] args) throws IOException, JsonMappingException {
        final int PORT = 8000;
        int sessionNo = 1;
        ServerMessage serverMessage;

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server has been started at port " + PORT + ".");

        while (true) {
            System.out.println("Waiting for the players..");

            Socket player1 = serverSocket.accept();
            System.out.println("Player1 has joined the game.");

            serverMessage = new ServerMessage(1, 0, 1000, false, false);
            String messagePlayer1 = objectWriter.writeValueAsString(serverMessage);
            serverMessage = new ServerMessage(2, 0, 1000, false, false);
            String messagePlayer2 = objectWriter.writeValueAsString(serverMessage);

            Socket player2 = serverSocket.accept();
            System.out.println("Player2 has joined the game.");

            new DataOutputStream(player1.getOutputStream()).writeUTF(messagePlayer1);
            new DataOutputStream(player2.getOutputStream()).writeUTF(messagePlayer2);

            new Thread(new Session(player1, player2)).start();
        }
    }
}
