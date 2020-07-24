package network.server;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.player.Player;
import network.client.Client;
import network.request.SignUpRequest;
import view.config.configmodels.ServerConfig;
import view.constants.Numbers;
import view.constants.Path;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

public class Server extends Thread{
    private int port = Numbers.DEFAULT_PORT;
    private String ip = "localhost";
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clientHandlers;

    public Server(ServerConfig config) throws IOException {
        port = config.getPort();
        ip = config.getIp();
        serverSocket = new ServerSocket(config.getPort());
        clientHandlers = new ArrayList<>();
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
                System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public synchronized int signUpPlayer(SignUpRequest signUpRequest) throws IOException {
        if (signUpRequest.getUsername() == null || signUpRequest.getUsername().equals("")) return 0; // user is empty;
        if (signUpRequest.getPassword() == null || signUpRequest.getPassword().equals("")) return 1; // pass is empty;
        ObjectMapper objectMapper = new ObjectMapper();
        //check if that file is exist or not!
        File file = new File(Path.PLAYER_FILE_PATH + signUpRequest.getUsername() + ".json");
        if (!file.exists()){
            Player player = new Player(signUpRequest.getUsername(), signUpRequest.getPassword());
            player.setDefaults();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, player);
            return 3; // User created successfully
        }else {
            return 2; // User exist
        }
    }
}
