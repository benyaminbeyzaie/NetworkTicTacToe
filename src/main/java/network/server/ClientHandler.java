package network.server;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.player.Player;
import network.request.GetSignedPlayerUsernameRequest;
import network.request.LoginRequest;
import network.request.Request;
import network.request.SignUpRequest;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread {
    Socket socket;
    Server server;
    private Player  signedPlayer;
    private String token = null;

    ClientHandler(Server server, Socket socket) throws IOException {
        this.socket = socket;
        this.server = server;
    }
    @Override
    public void run() {
        super.run();
            try {
                Scanner scanner = new Scanner(socket.getInputStream());
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                String requestString = null;
                while (!isInterrupted()){
                    System.out.println("waiting fo request...");
                    requestString = scanner.nextLine();
                    String response = executeRequest(requestString, this);
                    printStream.println(response);
                    printStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private String executeRequest(String jsonRequest, ClientHandler clientHandler) throws IOException {
        System.out.println("executing request...");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        System.out.println(jsonRequest);
        Request request = objectMapper.readValue(jsonRequest, SignUpRequest.class);
        if (request.getType().equals("SignUp")){
            SignUpRequest signUpRequest = objectMapper.readValue(jsonRequest, SignUpRequest.class);
            return server.signUpPlayer(signUpRequest);
        }
        if (request.getType().equals("Login")){
            LoginRequest loginRequest = objectMapper.readValue(jsonRequest, LoginRequest.class);
            return server.login(loginRequest, clientHandler);
        }
        if (request.getType().equals("GetSignedPlayerUsername")){
            System.out.println(jsonRequest);
            GetSignedPlayerUsernameRequest getSignedPlayerUsernameRequest = objectMapper.readValue(jsonRequest, GetSignedPlayerUsernameRequest.class);
            System.out.println("///");
            return server.getSignedPlayerUsername(getSignedPlayerUsernameRequest, clientHandler);
        }
        return null;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) throws IOException {
        this.token = token;
    }

    public Player getSignedPlayer() {
        return signedPlayer;
    }

    public void setSignedPlayer(Player signedPlayer) {
        this.signedPlayer = signedPlayer;
    }
}
