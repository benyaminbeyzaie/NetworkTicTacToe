package network.server;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.player.Player;
import network.request.LoginRequest;
import network.request.Request;
import network.request.SignUpRequest;
import network.response.LoginResponse;
import network.response.SignUpResponse;
import view.constants.Path;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
    private ServerWriter serverWriter;
    private ServerReader serverReader;
    private String token;
    private ObjectMapper objectMapper;
    private RequestExecutor requestExecutor;
    private Player signedPlayer;

    ClientHandler(Socket socket, Server server) throws IOException {
        serverReader = new ServerReader(socket.getInputStream());
        serverWriter = new ServerWriter(socket.getOutputStream());
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        requestExecutor = new RequestExecutor(server, this);
    }
    @Override
    public void run() {
        while (!isInterrupted()){
            try {
                executeRequest(serverReader.read());
                Thread.sleep(200);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void executeRequest(String jsonRequest) throws IOException {
        requestExecutor.executeRequest(jsonRequest);
        System.out.println(jsonRequest);
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) throws IOException {
        this.token = token;
    }

    public ServerWriter getServerWriter() {
        return serverWriter;
    }

    public Player getSignedPlayer() {
        return signedPlayer;
    }

    public void setSignedPlayer(Player signedPlayer) {
        this.signedPlayer = signedPlayer;
    }
}
