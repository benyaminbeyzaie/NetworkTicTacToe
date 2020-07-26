package network.server;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.player.Player;
import network.request.*;
import view.config.configmodels.ServerConfig;
import view.constants.Numbers;
import view.constants.Path;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Server extends Thread{
    private int port = Numbers.DEFAULT_PORT;
    private String ip = "localhost";
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clientHandlers;
    private TokenCreator tokenCreator;
    private PlayerStatusChecker playerStatusChecker;
    private ArrayList<Player> allPlayers;
    private AllPlayerFinder allPlayerFinder;

    public Server(ServerConfig config) throws IOException {
        port = config.getPort();
        ip = config.getIp();
        serverSocket = new ServerSocket(config.getPort());
        clientHandlers = new ArrayList<>();
        tokenCreator = new TokenCreator();
        allPlayers = new ArrayList<>();
        allPlayerFinder = new AllPlayerFinder(allPlayers);
        playerStatusChecker = new PlayerStatusChecker(clientHandlers, allPlayers);
    }



    @Override
    public void run() {
        super.run();
        System.out.println("Server started...");
        allPlayerFinder.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playerStatusChecker.start();
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
        boolean hasUsername = false;
        Player playerToSign = null;
        for (Player player :
                allPlayers) {
            if (player.getUsername().equals(loginRequest.getUsername())){
                hasUsername = true;
                playerToSign = player;
                break;
            }
        }
        if (hasUsername) {
            if (playerToSign.checkPassword(loginRequest.getPassword())) {
                String token = TokenCreator.generateNewToken();
                clientHandler.setToken(token);
                clientHandler.setSignedPlayer(playerToSign);
                clientHandler.getSignedPlayer().setStatus(1);
                playerToSign.savePlayerInfo();
                return token;
            } else {
                return "3";
            }
        } else {
            return "2";
        }
    }

    synchronized String getSignedPlayerUsername(GetSignedPlayerUsernameRequest getSignedPlayerUsernameRequest, ClientHandler clientHandler) throws IOException {
        System.out.println(clientHandler.getToken());
        if (getSignedPlayerUsernameRequest.getToken().equals(clientHandler.getToken())){
            return clientHandler.getSignedPlayer().getUsername();
        }else {
            return "error!";
        }
    }

    synchronized String getAllPlayersNameWithStatus(GetAllPlayersNameWithStatusRequest getAllPlayersNameWithStatusRequest, ClientHandler clientHandler) throws IOException {
        if (getAllPlayersNameWithStatusRequest.getToken().equals(clientHandler.getToken())){
            StringBuilder stringBuilder = new StringBuilder();

            for (Player player :
                    allPlayers) {
                System.out.println(player.getStatus());
                stringBuilder.append(player.getUsername());
                stringBuilder.append("/");
                stringBuilder.append(player.getScore());
                stringBuilder.append(player.getStatus());
                stringBuilder.append("-");
            }
            return stringBuilder.toString();
        }else {
            return "error!";
        }
    }

    synchronized String logOut(LogOutRequest logOutRequest) {
        for (ClientHandler c :
                clientHandlers) {
            if (logOutRequest.getToken().equals(c.getToken())) {
                c.getSignedPlayer().setStatus(0);
                c.setSignedPlayer(null);
            }
        }
        return "";
    }
}
