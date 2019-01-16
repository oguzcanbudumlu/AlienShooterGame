package com.company.Entities;



public class ClientMessage {
    private int status;
    private boolean shot;
    private boolean damaged;
    private int position;

    public ClientMessage() {}

    public ClientMessage(int status, boolean shot, boolean damaged, int position) {
        this.status = status;
        this.shot = shot;
        this.damaged = damaged;
        this.position = position;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean getShot() {
        return shot;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean getDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }
}
