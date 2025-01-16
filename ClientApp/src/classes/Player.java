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
    int socketNumber;

    //local Player
    public Player(String name) {
        this.name = name;
        this.password = null;
        this.email = null;
        this.mySocket = null;
        this.socketNumber = 0;
    }
    
    
    public Player(String name, String password, String email, Socket mySocket, int socketNumber) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.mySocket = mySocket;
        this.socketNumber = socketNumber;
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

    public int getSocketNumber() {
        return socketNumber;
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

    public void setSocketNumber(int socketNumber) {
        this.socketNumber = socketNumber;
    }
}
