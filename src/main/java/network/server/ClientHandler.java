package network.server;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
            try {
                Scanner scanner = new Scanner(socket.getInputStream());
                String requestString = null;
                while (!isInterrupted()){
                    System.out.println("waiting fo request...");
                    requestString = scanner.nextLine();
                    System.out.println(requestString);
                    executeRequest(requestString);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void executeRequest(String jsonRequest) throws IOException {
        System.out.println("executing request...");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SignUpRequest request = objectMapper.readValue(jsonRequest, SignUpRequest.class);
        if (request.getType().equals("SignUp")){
            SignUpRequest signUpRequest = objectMapper.readValue(jsonRequest, SignUpRequest.class);
            server.signUpPlayer(signUpRequest);
        }
    }
}