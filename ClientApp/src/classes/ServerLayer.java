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
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
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
    static BufferedReader inputStream;
    static String receivedmsg;
    static OnlineClientsListController onlineController;

    static {

        try {
            socketConnection = new Socket("127.0.0.1", 5005);
            outputStream = new PrintWriter(socketConnection.getOutputStream(), true);
            inputStream = new BufferedReader(new InputStreamReader(socketConnection.getInputStream()));

        } catch (IOException ex) {
            System.out.println("Server Connection ");
            connectionAlert();
        }

        new Thread(() -> {
            try {
                if (inputStream == null) {
                    System.out.println("Input stream is not initialized. Exiting thread.");
                    return;
                }
                while (true) {
                    receivedmsg = inputStream.readLine();
                    if (receivedmsg == null) {
                        System.out.println("Connection to server lost. Attempting to reconnect...");
                    } else {
                        Platform.runLater(() -> messageDeligator(receivedmsg));
                    }
                }
            } catch (IOException ex) {
                System.out.println("Error in socket thread: " + ex.getMessage());
                ex.printStackTrace();
            }
        }).start();

    }

    //authenticate
    public static void connectionAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Register");
        alert.setHeaderText(null);
        alert.setContentText("Server is closed");

        alert.showAndWait();
    }

    public static void messageDeligator(String msg) {
        JsonReader jsonReader = Json.createReader(new StringReader(msg));
        JsonObject jsonObject = jsonReader.readObject();
        switch (jsonObject.getString("Header")) {
            case "gameRequest":
                System.out.println("client2 received request");
                receiveGameRequest(jsonObject);
                break;
            case "gameAcceptanceResponce":
                System.out.println("client1 received Acceptance");
                receiveGameAcceptance(jsonObject);
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
        JsonObject jsonmsg = value
                .add("Header", "acceptGameRequest")
                .add("opponentUsername", invitingPlayer)
                .build();
        outputStream.println(jsonmsg.toString());

    }

    public static void receiveGameAcceptance(JsonObject jsonMsg) {
        //set variables needed by board here
        try {
            SceneController.navigateToXOBoard(null);
        } catch (IOException ex) {
            Logger.getLogger(ServerLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean reconnectToServer(String userName,String email,String password) {
        try {
            socketConnection = new Socket("127.0.0.1", 5005);
            outputStream = new PrintWriter(socketConnection.getOutputStream(), true);
            inputStream = new BufferedReader(new InputStreamReader(socketConnection.getInputStream()));
            System.out.println("Reconnected to the server.");

            return true;
        } catch (IOException ex) {
            System.out.println("Failed to reconnect to the server.");
            return false;
        }
    }

    public static void reRegisterClient(String userName,String email,String password) {
        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject registrationMessage = value
                .add("Header", "register")
                .add("username", userName)
                .add("password", password)
                .add("email", email)
                .build();
        System.out.println("u "+userName+"p "+password);
        outputStream.println(registrationMessage.toString());
        try {
            SceneController.navigateToOnlinePlayers(null);
        } catch (IOException ex) {
            Logger.getLogger(ServerLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Re-registration message sent.");
    }

}
