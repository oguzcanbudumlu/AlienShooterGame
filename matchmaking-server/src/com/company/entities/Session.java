package com.company.entities;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.company.utilities.Constants.*;


/**
 * For two matched players, one
 * session is created. Data exchange between
 * players is done by Session's. Of course,
 * Server acts a bridge during this exchange
 * process.
 */
public class Session implements Runnable {
    private Socket player1;
    private Socket player2;

    private DataInputStream fromPlayer1;
    private DataOutputStream toPlayer1;
    private DataInputStream fromPlayer2;
    private DataOutputStream toPlayer2;

    private ServerMessage msgToPlayer1;
    private ServerMessage msgToPlayer2;

    private ClientMessage msgFromPlayer1;
    private ClientMessage msgFromPlayer2;

    private int health = INIT_HEALTH;

    // initial positions for players
    private int posPlayer1 = INIT_POS_OF_PLAYER1;
    private int posPlayer2 = INIT_POS_OF_PLAYER2;

    private int shotCountPlayer1 = 0;
    private int shotCountPlayer2 = 0;

    Gson gson = new Gson(); // in order to convert ClientMessage and ServerMessage to String format or vice versa

    public Session(Socket player1, Socket player2) throws IOException {
        this.player1 = player1;
        this.player2 = player2;

        fromPlayer1 =  new DataInputStream(player1.getInputStream());
        toPlayer1 = new DataOutputStream(player1.getOutputStream());
        fromPlayer2 = new DataInputStream(player2.getInputStream());
        toPlayer2 = new DataOutputStream(player2.getOutputStream());

        msgToPlayer1 = new ServerMessage(3, posPlayer1, health, false, false);
        msgToPlayer2 = new ServerMessage(3, posPlayer2, health, false, false);

        msgFromPlayer1 = new ClientMessage(0, false, false, 0);
        msgFromPlayer2 = new ClientMessage(0, false, false, 0);
    }

    @Override
    public void run() {
        new Thread(() -> {
            try {
                while (true) {
                    if (health < LOWER_HEALTH_LIMIT) {
                        msgToPlayer1.setStatus(4);
                        msgToPlayer1.setWon(false);
                        transmitMessage(player1);
                        break;
                    }
                    System.out.println("Waiting a message from player1..");
                    receiveMessage(player1);
                    if (msgFromPlayer1.getDamaged()) {
                        health -= HEALTH_DROP_AMOUNT;
                        if (health < LOWER_HEALTH_LIMIT) {
                            msgToPlayer1.setStatus(4);
                            msgToPlayer1.setWon(true);
                            transmitMessage(player1);
                            break;
                        }
                    }
                    if (msgFromPlayer1.getShot()) {
                        shotCountPlayer1++;
                    }
                    msgToPlayer1.setShot(false);
                    msgToPlayer1.setStatus(3);
                    msgToPlayer1.setHealth(health);
                    msgToPlayer1.setPosition(msgFromPlayer2.getPosition());
                    if (shotCountPlayer2 > 0) {
                        System.out.println("shotCountPlayer2 : " + shotCountPlayer2);
                        msgToPlayer1.setShot(true);
                        shotCountPlayer2--;
                    }
                    msgToPlayer1.setWon(false);
                    transmitMessage(player1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                while (true) {
                    if (health < LOWER_HEALTH_LIMIT) {
                        msgToPlayer2.setStatus(4);
                        msgToPlayer2.setWon(false);
                        transmitMessage(player2);
                        break;
                    }
                    System.out.println("Waiting a message from player2..");
                    receiveMessage(player2);
                    if (msgFromPlayer2.getDamaged()) {
                        health -= HEALTH_DROP_AMOUNT;
                        if (health < LOWER_HEALTH_LIMIT) {
                            msgToPlayer2.setStatus(4);
                            msgToPlayer2.setWon(true);
                            transmitMessage(player2);
                            break;
                        }
                    }
                    if (msgFromPlayer2.getShot()) {
                        shotCountPlayer2++;
                    }
                    msgToPlayer2.setShot(false);
                    msgToPlayer2.setStatus(3);
                    msgToPlayer2.setHealth(health);
                    msgToPlayer2.setPosition(msgFromPlayer1.getPosition());
                    if (msgFromPlayer1.getShot()) shotCountPlayer1++;
                    if (shotCountPlayer1 > 0) {
                        System.out.println("shotCountPlayer2 : " + shotCountPlayer2);
                        msgToPlayer2.setShot(true);
                        shotCountPlayer1--;
                    }
                    msgToPlayer2.setWon(false);
                    transmitMessage(player2);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void transmitMessage(Socket player) throws IOException {
        if (player == player1) {
            String message = gson.toJson(msgToPlayer1);
            toPlayer1.writeUTF(message);
        }
        else {
            String message = gson.toJson(msgToPlayer2);
            toPlayer2.writeUTF(message);
        }
    }

    private void receiveMessage(Socket player) throws IOException {
        if (player == player1) {
            String response = fromPlayer1.readUTF();
            String message = new String(response.getBytes(), "UTF-8");
            msgFromPlayer1 = gson.fromJson(message, ClientMessage.class);

        }
        else {
            String response = fromPlayer2.readUTF();
            String message = new String(response.getBytes(), "UTF-8");
            msgFromPlayer2 = gson.fromJson(message, ClientMessage.class);

        }
    }

}
