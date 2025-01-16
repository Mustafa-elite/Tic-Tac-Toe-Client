/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class ServerLayer {
    static Socket socketConnection;
    static PrintWriter outputStream;
    static BufferedReader inputStream;
    static Queue<String> serverMsg = new LinkedList<>();
    
    static {
        try {
            socketConnection = new Socket("127.0.0.1", 5005);
            outputStream=new PrintWriter(socketConnection.getOutputStream());
            inputStream= new BufferedReader(new InputStreamReader(socketConnection.getInputStream()));
        } catch (IOException ex) {
            System.out.println("Server Connection ");
            ex.printStackTrace();
        }
        
        new Thread(()->{
            try {
                while(true)
                {
                    
                    serverMsg.add(inputStream.readLine());
                }
                
            } catch (IOException ex) {
                Logger.getLogger(ServerLayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        //////////////////////
        //////////////////////
        //////////////////////
        //////////////////////
        //////////////////////
        //////////////////////
    }
    //authenticate
    
    
}
