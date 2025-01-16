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
import java.math.BigDecimal;
import java.net.Socket;
import javafx.application.Platform;
import javax.json.*;
//import com.google.gson.JsonObject;

/**
 *
 * @author user
 */
public class ServerLayer {
    static Socket socketConnection;
    static PrintWriter outputStream;
    static BufferedReader inputStream;
    static String receivedmsg;
    
    static {
        
        try {
            socketConnection = new Socket("127.0.0.1", 5005);
            outputStream=new PrintWriter(socketConnection.getOutputStream(),true);
            inputStream= new BufferedReader(new InputStreamReader(socketConnection.getInputStream()));
            
        } catch (IOException ex) {
            System.out.println("Server Connection ");
            ex.printStackTrace();
        }
        
        new Thread(()->{
            try {
                while(true)
                {
                    receivedmsg=inputStream.readLine();
                    Platform.runLater(()->{
                        messageDeligator(receivedmsg);
                    });
                    
                }
                
            } catch (IOException ex) {
                System.out.println("creation of socket thread ");
                ex.printStackTrace();
            }
        }).start();

    }
    //authenticate
    
    public static void messageDeligator(String msg)
    {
        
    }
    public static void messageTest()
    {
        JsonObjectBuilder value = Json.createObjectBuilder();
        //value.add("test", BigDecimal.ONE)
        JsonObject jsonmsg= value
                .add("Header", "authenticate")
                .add("email", "test@gmail.com")
                .build();
        System.out.println(jsonmsg.toString()); 
        
       
        outputStream.println(jsonmsg.toString());
        
                
        
    }
}
