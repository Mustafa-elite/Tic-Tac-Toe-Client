/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.net.Socket;

/**
 *
 * @author a
 */
public class Player {
    String name;
    String password;
    String email;
    Socket mySocket;
    int score;
    boolean available;

    //local Player
    public Player(String name) {
        this.name = name;
        this.password = null;
        this.email = null;
        this.mySocket = null;
        this.score=0;
        this.available = false;
    }
    
    
    public Player(String name, String email, Socket mySocket,int score) {
        this.name = name;
        this.email = email;
        this.mySocket = mySocket;
        this.score=score;
        this.available = false;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Socket getMySocket() {
        return mySocket;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMySocket(Socket mySocket) {
        this.mySocket = mySocket;
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


}
