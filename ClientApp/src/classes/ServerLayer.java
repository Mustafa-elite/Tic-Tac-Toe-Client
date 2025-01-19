/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import clientapp.controllers.OnlineClientsListController;

import clientapp.controllers.SceneController;
import clientapp.controllers.RegisterationController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
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
    static ObservableList<Player> onlinePlayersList = FXCollections.observableArrayList();
    static BufferedReader inputStream;
    static String receivedmsg;
    static OnlineClientsListController onlineController;

    //login string response 
    private static String response = "" ; 
    public static boolean flagCheckResponse = false ; 


    public static Socket getSocketConnection() {
        return socketConnection;
    }

    public static PrintWriter getOutputStream() {
        return outputStream;
    }

    public static BufferedReader getInputStream() {
        return inputStream;
    }

    public static String getReceivedmsg() {
        return receivedmsg;
    }

    public static OnlineClientsListController getOnlineController() {
        return onlineController;
    }

    public static void setSocketConnection(Socket socketConnection) {
        ServerLayer.socketConnection = socketConnection;
    }

    public static void setOutputStream(PrintWriter outputStream) {
        ServerLayer.outputStream = outputStream;
    }

    public static void setInputStream(BufferedReader inputStream) {
        ServerLayer.inputStream = inputStream;
    }

    public static void setReceivedmsg(String receivedmsg) {
        ServerLayer.receivedmsg = receivedmsg;
    }

    public static void setOnlineController(OnlineClientsListController onlineController) {
        ServerLayer.onlineController = onlineController;
    }
    public static ObservableList<Player> getOnlinePlayersList() {
        return onlinePlayersList;
    }

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


    public static void messageDeligator(String msg) {
        JsonReader jsonReader = Json.createReader(new StringReader(msg));
        JsonObject jsonObject = jsonReader.readObject();
        switch(jsonObject.getString("Header"))
        {
            case "gameRequest":
                System.out.println("client2 received request");
                receiveGameRequest(jsonObject);
                break;
            case "onlinePlayersList":
                System.out.println("test0");
                updateOnlinePlayersList(jsonObject); 
                System.out.println(jsonObject.toString());
                break;
            case "gameAcceptanceResponce":
                System.out.println("client1 received Acceptance");
                receiveGameAcceptance(jsonObject);
                break;
            case "registerResponse":
                boolean success = jsonObject.getBoolean("success");
                String message = jsonObject.getString("message");
                System.out.println("message:" + message);

                Platform.runLater(() -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(ServerLayer.class.getResource("/clientapp/views/Registeration.fxml"));
                        Pane root = loader.load();
                        RegisterationController controller = loader.getController();
                        if (controller != null) {
                            System.out.println("controller not null");
                            controller.updateRegistrationStatusLabel(message);

                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ServerLayer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                break;
            

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


    public static void sendGameAcceptance(String invitingPlayer) {
        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject jsonmsg= value
                .add("Header", "acceptGameRequest")
                .add("opponentUsername", invitingPlayer)
                

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

    public static void receiveGameAcceptance(JsonObject jsonMsg)
    {
        //set variables needed by board here
        try {
            SceneController.navigateToXOBoard(null);
        } catch (IOException ex) {
            Logger.getLogger(ServerLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void requestOnlinePlayers() {
        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject jsonmsg = value
                .add("Header", "getOnlinePlayers") 
                .build();
         System.out.println("test1");
        outputStream.println(jsonmsg.toString());
        System.out.println("test2");
    }
  
    public static void updateOnlinePlayersList(JsonObject jsonMsg) {
        onlinePlayersList.clear();
        JsonArray playersArray = jsonMsg.getJsonArray("players"); 
        for (JsonValue playerValue : playersArray) {
            JsonObject playerObject = (JsonObject) playerValue;
            String username = playerObject.getString("username");
            onlinePlayersList.add(new Player(username));
        }

    }
}

