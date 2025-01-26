/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import clientapp.ClientApp;
import clientapp.controllers.LoginController;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;
import javafx.stage.Window;

import clientapp.controllers.BoardController;
import clientapp.controllers.HomePageContoller;
import java.math.BigDecimal;

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

    static BoardController boredConrtoller;
   
    static LoginController loginController;
    private static String serverIP ;
    private static int serverPort ;
    static private Player myPlayer = null;
    static private Player opponentPlayer = null;

    //login string response 
    private static String response = "";

    // string to contain the request sender 
    //public static String player2 = " ";
    //flag to check the turn response 
    public static int secondPlayerPosition = -1;
    public static boolean invitingFlag;
    private static int opponentScore;

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

    public static ObservableList<Player> getOnlinePlayersList() {
        return onlinePlayersList;
    }

    public static void setBoredConrtoller(BoardController boredConrtoller) {
        ServerLayer.boredConrtoller = boredConrtoller;
    }

    public static void setLoginController(LoginController loginController) {
        ServerLayer.loginController = loginController;
    }

    public static Player getMyPlayer() {
        return myPlayer;
    }

    public static void setMyPlayer(Player myPlayer) {
        ServerLayer.myPlayer = myPlayer;
    }

    public static Player getOpponentPlayer() {
        return opponentPlayer;
    }

    public static void setOpponentPlayer(Player opponentPlayer) {
        ServerLayer.opponentPlayer = opponentPlayer;
    }

    public static void setServerIP(String ip) {
        serverIP = ip;
    }

    public static void setServerPort(int port) {
        serverPort = port;
    }

    static {

        try {
            serverIP=HomePageContoller.ipAddress;
            serverPort=HomePageContoller.port;
            socketConnection = new Socket(serverIP, serverPort);
            outputStream = new PrintWriter(socketConnection.getOutputStream(), true);
            inputStream = new BufferedReader(new InputStreamReader(socketConnection.getInputStream()));

            System.out.println("Server Connected successfully ");

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

    //authenticate
    //System.out.println(jsonObject.getString("Header"));
    public static void messageDeligator(String msg) {
        JsonReader jsonReader = Json.createReader(new StringReader(msg));
        JsonObject jsonObject = jsonReader.readObject();
        switch (jsonObject.getString("Header")) {
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
                registerResponse(jsonObject);
                break;

            case "loginResponse":
                System.out.println("login response in client " + msg);
                response = msg;
                loginResponse();
                break;
            case "XOPlay":
                reveicedPlay(jsonObject);
                break;
            case "opponentRetreat":
                opponentRetreat(jsonObject);
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

    public static void sendPlayRequest(String playername) {

        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject jsonmsg = value
                .add("Header", "gameRequest")
                .add("username", playername)
                .add("myScore", myPlayer.getScore())
                .build();
        outputStream.println(jsonmsg.toString());
        // take the requestSender contain the player name
        //opponentPlayer = new Player(playername);

    }

    public static void receiveGameRequest(JsonObject jsonMsg) {
        System.out.println(jsonMsg.getString("username"));
        //opponentPlayer = new Player(jsonMsg.getString("username"));
        opponentScore=jsonMsg.getInt("opponentScore");
        //opponentPlayer.setScore();
        GameRequestManager.getInstance().displayRequest(jsonMsg.getString("username"), ClientApp.getPrimaryStage().getScene());
        //onlineController.displayGameRequest(jsonMsg.getString("username"));
    }

    public static void sendGameAcceptance(String invitingPlayer) {
        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject jsonmsg = value
                .add("Header", "acceptGameRequest")
                .add("opponentUsername", invitingPlayer)
                .add("myScore", myPlayer.getScore())
                .build();
        outputStream.println(jsonmsg.toString());
        invitingFlag = false;
        opponentPlayer = new Player(invitingPlayer);
        opponentPlayer.setScore(opponentScore);
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
            myPlayer = new Player(userName);
            outputStream.println(loginObject);
            System.out.println("login request : " + loginObject);
        } else {
            System.out.println("Output stream is not initialized. Check server connection.");
        }

    }

    public static boolean loginResponse() {
        boolean checkResponse;
        JsonReader jsonReader = Json.createReader(new StringReader(response));
        JsonObject jsonObject = jsonReader.readObject();
        checkResponse = jsonObject.getBoolean("success");
        if (checkResponse) {
            System.out.println("user found (client)");
            try {
                myPlayer.setEmail(jsonObject.getString("email"));
                myPlayer.setScore(jsonObject.getInt("score"));
                SceneController.navigateToOnlinePlayers(null);
            } catch (IOException ex) {
                System.out.println("error navigating to online players after log in");
                ex.printStackTrace();
            }
            return true;
        } else {
            loginController.userNotAvailableAction();
        }
        System.out.println("user not found (client)");
        return false;
    }

    public static void receiveGameAcceptance(JsonObject jsonMsg) {
        //set variables needed by board here
        try {
            opponentPlayer= new Player(jsonMsg.getString("opponentUsername"));
            opponentPlayer.setScore(jsonMsg.getInt("opponentScore"));
            invitingFlag=true;
            SceneController.navigateToXOBoard(null);
        } catch (IOException ex) {
            Logger.getLogger(ServerLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public static boolean reconnectToServer(String userName, String email, String password) {
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

    public static void reRegisterClient(String userName, String email, String password) {
        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject registrationMessage = value
                .add("Header", "register")
                .add("username", userName)
                .add("password", password)
                .add("email", email)
                .build();
        System.out.println("u " + userName + "p " + password);
        myPlayer = new Player(userName, email, socketConnection, 100);
        outputStream.println(registrationMessage.toString());
        try {
            SceneController.navigateToOnlinePlayers(null);
        } catch (IOException ex) {
            Logger.getLogger(ServerLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Re-registration message sent.");
    }

    public static void requestOnlinePlayers() {
        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject jsonmsg = value
                .add("Header", "getOnlinePlayers")
                .build();
        //System.out.println("test1");
        outputStream.println(jsonmsg.toString());
        //System.out.println("test2");
    }

    public static void updateOnlinePlayersList(JsonObject jsonMsg) {
        onlinePlayersList.clear();
        JsonArray playersArray = jsonMsg.getJsonArray("players");
        for (JsonValue playerValue : playersArray) {
            JsonObject playerObject = (JsonObject) playerValue;
            String username = playerObject.getString("username");
            boolean isAvailable = playerObject.getBoolean("available"); 
            Player player = new Player(username);
            player.setAvailable(isAvailable);
            onlinePlayersList.add(player);
        }

    }

    public static void sendCurrentPlay(String player, int position, String winnerName) {
        JsonObjectBuilder jsonmsg = Json.createObjectBuilder()
                .add("Header", "sendXOPlay")
                .add("player", player)
                .add("position", position);
        if (winnerName != null) {
            jsonmsg.add("winnerName", winnerName);
        }
        String XOmessage = jsonmsg.build().toString();
        outputStream.println(XOmessage);
        if (outputStream != null) {
            System.out.println("in the cuttent play method ");
        } else {
            System.out.println("outputStream is null");
        }

    }

    public static void reveicedPlay(JsonObject receivedJsonObject) {

        secondPlayerPosition = receivedJsonObject.getInt("position");
        boredConrtoller.callButtonHandller();
    }

    public static void sendLogoutRequest() {
        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject jsonmsg = value
                .add("Header", "Logout")
                .add("username", myPlayer.getName())
                .build();
        
        if(opponentPlayer!=null)
        {
            retreatRequest();
        }
        myPlayer=null;
        outputStream.println(jsonmsg.toString());
        //terminateAppFlag=true;

    }

    public static void registerRequest(String username, String password, String email) {
        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject jsonmsg = value
                .add("Header", "register")
                .add("username", username)
                .add("password", password)
                .add("email", email)
                .build();

        myPlayer = new Player(username, email, socketConnection, 100);/////////////////
        outputStream.println(jsonmsg.toString());
        System.out.println("Registration message sent: " + jsonmsg);
    }

    private static void registerResponse(JsonObject jsonObject) {

        boolean success = jsonObject.getBoolean("success");
        String message = jsonObject.getString("message");
        System.out.println("message:" + message);
        try {
            FXMLLoader loader = new FXMLLoader(ServerLayer.class.getResource("/clientapp/views/Registeration.fxml"));
            Pane root = loader.load();
            RegisterationController controller = loader.getController();
            if (controller != null) {
                System.out.println("controller not null");
                controller.updateRegistrationStatusLabel(message,success);

            }
        } catch (IOException ex) {
            Logger.getLogger(ServerLayer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void retreatRequest()
    {
        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject jsonmsg = value
                .add("Header", "retreat")
                .add("username", opponentPlayer.getName())
                .build();
        outputStream.println(jsonmsg.toString());
        if(GamePlay.record)
        {
            boredConrtoller.boardCloseRecord();
        }
        opponentPlayer=null;
        myPlayer.setScore(myPlayer.getScore()-10);

    }

    private static void opponentRetreat(JsonObject jsonObject) {
        myPlayer.setScore(myPlayer.getScore()+10);
        opponentPlayer=null;
        boredConrtoller.onlineOppRetreat();
    }

}