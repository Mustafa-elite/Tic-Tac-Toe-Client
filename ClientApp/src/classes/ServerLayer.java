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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
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
    static BlockingQueue<String> generalServerMsg = new LinkedBlockingQueue<>();
    static BlockingQueue<String> responseServerMsg = new LinkedBlockingQueue<>();
    
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
            String msg;
            try {
                while(true)
                {
                    msg=inputStream.readLine();
                    if(msg.startsWith("General:")) 
                         generalServerMsg.put(msg);
                    else responseServerMsg.put(msg);
                }
                
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(ServerLayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        
        new Thread(()->{
            String msg;
            while(true)
            {
                
                try {
                    msg = generalServerMsg.take();
                    //Let msg= "General:sout"/
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServerLayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
        
        new Thread(()->{
            String msg;
            while(true)
            {
                
                try {
                    msg = responseServerMsg.take();
                    /*what the message contains*/
                    //let msg= Authentication:Success//
                    //requestAuthentication(username,password);send data to server to authenticate
                    //acceptAuthentication(msg);accepted or not
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServerLayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();

    }
    //authenticate
    
    
}
