package network.server;

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

public class Server extends Thread{
    private int port = Numbers.DEFAULT_PORT;
    private String ip = "localhost";
    private ServerSocket serverSocket;
    private PlayerStatusChecker playerStatusChecker;
    private ArrayList<Player> allPlayers;
    private AllPlayerFinder allPlayerFinder;
    private ArrayList<ClientHandler> clientHandlers;

    public Server(ServerConfig config) throws IOException {
        port = config.getPort();
        ip = config.getIp();
        serverSocket = new ServerSocket(config.getPort());
        allPlayers = new ArrayList<>();
        clientHandlers = new ArrayList<>();
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
                ClientHandler clientHandler = new ClientHandler(socket, this);
                clientHandlers.add(clientHandler);
                clientHandler.start();
                System.out.println("CLIENT-" + clientHandlers.size() +  " HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }
}
