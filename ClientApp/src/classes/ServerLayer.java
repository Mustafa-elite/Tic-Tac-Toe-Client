/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import clientapp.controllers.OnlineClientsListController;
import clientapp.controllers.SceneController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    static OnlineClientsListController onlineController;
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
        JsonReader jsonReader = Json.createReader(new StringReader(msg));
        JsonObject jsonObject = jsonReader.readObject();

        
        //System.out.println(jsonObject.getString("Header"));
        switch(jsonObject.getString("Header"))
        {
            case "gameRequest":
                System.out.println("client2 received request");
                receiveGameRequest(jsonObject);
                break;
            case "gameAcceptanceResponce":
                System.out.println("client1 received Acceptance");
                receiveGameAcceptance(jsonObject);
                
            
        }
    }
    
    
    
    
    
    
    
    public static void messageTest()
    {
        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject jsonmsg= value
                .add("Header", "authenticate")
                .add("myname", "ahmed")
                //.add("myname", "Mohammed")
                
                .build();
        System.out.println(jsonmsg.toString());
        outputStream.println(jsonmsg.toString());     
    }
   
    public static void setonlineController(OnlineClientsListController controller) {
        onlineController = controller;
    }
    
    
    public static void sendPlayRequest(String playername)
    {
        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject jsonmsg= value
                .add("Header", "gameRequest")
                .add("username", playername)
                
                .build();
        outputStream.println(jsonmsg.toString());
        
    }
    public static void receiveGameRequest(JsonObject jsonMsg)
    {
        System.out.println(jsonMsg.getString("username"));
        
        onlineController.displayGameRequest(jsonMsg.getString("username"));
    }

    public static void sendGameAcceptance(String invitingPlayer) {
        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject jsonmsg= value
                .add("Header", "acceptGameRequest")
                .add("opponentUsername", invitingPlayer)
                
                .build();
        outputStream.println(jsonmsg.toString());
        
    }
    
    public static void receiveGameAcceptance(JsonObject jsonMsg)
    {
        //set variables needed by board here
        try {
            SceneController.navigateToXOBoard(null);
        } catch (IOException ex) {
            Logger.getLogger(ServerLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
