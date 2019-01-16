package com.company.entities;

public class ServerMessage {
    private int status;
    private int position;
    private int health;
    private boolean shot;
    private boolean won;

    public ServerMessage() {}

    public ServerMessage(int status, int position, int health, boolean shot, boolean won) {
        this.status = status;
        this.position = position;
        this.health = health;
        this.shot = shot;
        this.won = won;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean getWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public boolean getShot() {
        return shot;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }
}
