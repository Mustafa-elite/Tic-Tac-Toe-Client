/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import clientapp.controllers.OnlineClientsListController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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
    //login string response 
    private static String response = "" ; 
    public static boolean flagCheckResponse = false ; 
    static {

        try {
            socketConnection = new Socket("127.0.0.1", 5005);
            outputStream = new PrintWriter(socketConnection.getOutputStream(), true);
            inputStream = new BufferedReader(new InputStreamReader(socketConnection.getInputStream()));
            System.out.println("Server Connected successfully ");

        } catch (IOException ex) {
            System.out.println("Server Connection ");
            ex.printStackTrace();
        }

        new Thread(() -> {
            try {
                while (true) {
                    receivedmsg = inputStream.readLine();
                    Platform.runLater(() -> {
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
    public static void messageDeligator(String msg) {
        JsonReader jsonReader = Json.createReader(new StringReader(msg));
        JsonObject jsonObject = jsonReader.readObject();

        //System.out.println(jsonObject.getString("Header"));
        switch (jsonObject.getString("Header")) {
            case "gameRequest":
                System.out.println("client2 received request");
                receiveGameRequest(jsonObject);
            case "loginResponse":
                System.out.println("login response in client " + msg);
                 response = msg ; 
                loginResponse();
                break ; 

        }
    }

    public static void messageTest() {
        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject jsonmsg = value
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

    public static void sendPlayRequest(String playername) {
        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject jsonmsg = value
                .add("Header", "gameRequest")
                .add("username", playername)
                .build();
        outputStream.println(jsonmsg.toString());

    }

    public static void receiveGameRequest(JsonObject jsonMsg) {
        System.out.println(jsonMsg.getString("username"));

        onlineController.displayGameRequest(jsonMsg.getString("username"));
    }

    public static void loginRequest(String userName, String password) {
        JsonObjectBuilder loginJonObject = Json.createObjectBuilder();
        JsonObject obj = loginJonObject
                .add("Header", "login")
                .add("user-name", userName)
                .add("password", password)
                .build();
        String loginObject = obj.toString();
        if (outputStream != null) {
            outputStream.println(loginObject);
            System.out.println("login request : " + loginObject);
        } else {
            System.out.println("Output stream is not initialized. Check server connection.");
        }

    }

    public static boolean loginResponse() {
        boolean checkResponse ;
        JsonReader jsonReader = Json.createReader(new StringReader(response));
        JsonObject jsonObject = jsonReader.readObject();
        checkResponse = jsonObject.getBoolean("success");
        if (checkResponse) {
            System.out.println("user found (client)");
            flagCheckResponse = true ; 
            return true ; 
        }
        System.out.println("user not found (client)");
          flagCheckResponse = true ; 
        return false ;
    }
}
