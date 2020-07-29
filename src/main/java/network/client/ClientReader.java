package network.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import network.request.GetSignedPlayerUsernameRequest;
import network.response.GetSignedPlayerUsernameResponse;
import network.response.LoginResponse;
import network.response.Response;
import network.response.SignUpResponse;

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
    }
}
