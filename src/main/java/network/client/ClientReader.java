package network.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import network.request.GetSignedPlayerUsernameRequest;
import network.response.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;

public class ClientReader extends Thread {
    private Scanner scanner;
    private ObjectMapper objectMapper;
    private Client client;
    ClientReader(DataInputStream dataInputStream, Client client){
        scanner = new Scanner(dataInputStream);
        this.client = client;
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    @Override
    public void run() {
        super.run();
        while (!interrupted()){
            try {
                executeResponse(scanner.nextLine());
                Thread.sleep(200);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void executeResponse(String jsonResponse) throws IOException {
        System.out.println("Response: " + jsonResponse );
        Response response = objectMapper.readValue(jsonResponse, Response.class);
        if (response.getType().equals("SignUp")){
            SignUpResponse signUpResponse = objectMapper.readValue(jsonResponse, SignUpResponse.class);
            client.getStateManager().getStateContainer().getSignUpState().showDialogBox(signUpResponse.getStatus());
        }
        if (response.getType().equals("Login")){
            LoginResponse loginResponse = objectMapper.readValue(jsonResponse, LoginResponse.class);
            client.getStateManager().getStateContainer().getLoginState().showDialogBox(loginResponse.getStatus());
        }
        if (response.getType().equals("Username")){
            GetSignedPlayerUsernameResponse  getSignedPlayerUsernameResponse = objectMapper.readValue(jsonResponse, GetSignedPlayerUsernameResponse.class);
            client.getStateManager().getStateContainer().getMenuState().getUsernameLabel().setText(getSignedPlayerUsernameResponse.getUsername());
        }
        if (response.getType().equals("AllPlayer")){
            GetAllPlayerResponse getAllPlayerResponse = objectMapper.readValue(jsonResponse, GetAllPlayerResponse.class);
            String[] names = getAllPlayerResponse.getAllPlayersByDash().split("/");
            StringBuilder namesInTextArea = new StringBuilder();
            for (String name : names) {
                namesInTextArea.append(name);
                namesInTextArea.append("\n");
            }
            client.getStateManager().getStateContainer().getMenuState().getTextArea().setText(namesInTextArea.toString());
        }
    }
}
