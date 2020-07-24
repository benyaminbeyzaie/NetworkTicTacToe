package network.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import network.request.Request;
import network.request.SignUpRequest;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread {
    Socket socket;
    Server server;

    ClientHandler(Server server, Socket socket){
        this.socket = socket;
        this.server = server;
    }
    @Override
    public void run() {
        super.run();
        while (!isInterrupted()){
            try {
                Scanner scanner = new Scanner(socket.getInputStream());
                while(true) {
                    executeRequest(scanner.nextLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void executeRequest(String jsonRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Request request = objectMapper.readValue(jsonRequest, Request.class);
        if (request.getType().equals("SignUp")){
            SignUpRequest signUpRequest = objectMapper.readValue(jsonRequest, SignUpRequest.class);
            server.signUpPlayer(signUpRequest);
        }
    }
}
