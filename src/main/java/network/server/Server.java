package network.server;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.player.Player;
import network.request.LoginRequest;
import network.request.SignUpRequest;
import view.config.configmodels.ServerConfig;
import view.constants.Numbers;
import view.constants.Path;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{
    private int port = Numbers.DEFAULT_PORT;
    private String ip = "localhost";
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clientHandlers;
    private TokenCreator tokenCreator;

    public Server(ServerConfig config) throws IOException {
        port = config.getPort();
        ip = config.getIp();
        serverSocket = new ServerSocket(config.getPort());
        clientHandlers = new ArrayList<>();
        tokenCreator = new TokenCreator();
    }



    @Override
    public void run() {
        super.run();
        System.out.println("Server started...");
        listenForJoinRequest();
    }

    private void listenForJoinRequest() {
        Socket socket = null;
        try {
            while (!isInterrupted()){
                System.out.println("Listening for join clients...");
                socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(this, socket);
                clientHandlers.add(clientHandler);
                clientHandler.start();
                System.out.println("CLIENT-" + clientHandlers.size() + " HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    synchronized String signUpPlayer(SignUpRequest signUpRequest) throws IOException {
        if (signUpRequest.getUsername() == null || signUpRequest.getUsername().equals("")) return "0"; // user is empty;
        if (signUpRequest.getPassword() == null || signUpRequest.getPassword().equals("")) return "1"; // pass is empty;
        ObjectMapper objectMapper = new ObjectMapper();
        //check if that file is exist or not!
        File file = new File(Path.PLAYER_FILE_PATH + signUpRequest.getUsername() + ".json");
        if (!file.exists()){
            Player player = new Player(signUpRequest.getUsername(), signUpRequest.getPassword());
            player.setDefaults();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, player);
            return "3"; // User created successfully
        }else {
            return "2"; // User exist
        }
    }


    synchronized String login(LoginRequest loginRequest, ClientHandler clientHandler) throws IOException {
        if (loginRequest.getUsername().equals("")) return "0";
        else if (loginRequest.getPassword().equals("")) return "1";
        File file = new File(Path.PLAYER_FILE_PATH + loginRequest.getUsername() + ".json");
        if (file.exists()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Player player = objectMapper.readValue(file, Player.class);
            if (player.checkPassword(loginRequest.getPassword())) {
                String token = TokenCreator.generateNewToken();
                clientHandler.setToken(token);
                player.savePlayerInfo();
                return token;
            } else {
                return "3";
            }
        } else {
            return "2";
        }
    }
}
