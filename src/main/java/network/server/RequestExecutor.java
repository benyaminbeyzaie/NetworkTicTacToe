package network.server;

import com.fasterxml.jackson.core.JsonProcessingException;
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

public class RequestExecutor {
    ServerWriter serverWriter;
    ObjectMapper objectMapper;
    ClientHandler clientHandler;
    Server server;

    RequestExecutor(Server server, ClientHandler clientHandler){
        this.server = server;
        this.clientHandler = clientHandler;
        serverWriter = clientHandler.getServerWriter();
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    public void executeRequest(String jsonRequest) throws IOException {
        Request request = objectMapper.readValue(jsonRequest, Request.class);

        if (request.getType().equals("SignUp")){
            SignUpRequest signUpRequest = objectMapper.readValue(jsonRequest, SignUpRequest.class);
            signUp(signUpRequest);
        }
        if (request.getType().equals("Login")){
            LoginRequest loginRequest = objectMapper.readValue(jsonRequest, LoginRequest.class);
            login(loginRequest);
        }
    }

    private void login(LoginRequest loginRequest) throws IOException {
        if (loginRequest.getUsername() == null || loginRequest.getUsername().equals("")){
            serverWriter.writeResponse(new LoginResponse("0"));
        }else if (loginRequest.getPassword() == null || loginRequest.getPassword().equals("")){
            serverWriter.writeResponse(new LoginResponse("1"));
        }else {
            if (!usernameExist(loginRequest.getUsername())) serverWriter.writeResponse(new LoginResponse("2"));
            else {
                File playerToSignFile = new File(Path.PLAYER_FILE_PATH + loginRequest.getUsername() + ".json");
                Player playerToSign = objectMapper.readValue(playerToSignFile, Player.class);
                boolean playerIsAlreadyOnline = false;
                for (Player p :
                        server.getAllPlayers()) {
                    if (p.getUsername().equals(playerToSign.getUsername())){
                        if (p.getStatus() == 1){
                            playerIsAlreadyOnline = true;
                        }
                        break;
                    }
                }
                if (playerIsAlreadyOnline){
                    serverWriter.writeResponse(new LoginResponse("4"));
                }else{
                    if (loginRequest.getPassword().equals(playerToSign.getPassword())){
                        String token = TokenCreator.generateNewToken();
                        clientHandler.setToken(token);
                        clientHandler.setSignedPlayer(playerToSign);
                        LoginResponse loginResponse = new LoginResponse(token);
                        serverWriter.writeResponse(loginResponse);
                    }else {
                        serverWriter.writeResponse(new LoginResponse("3"));
                    }
                }

            }
        }

    }

    private void signUp(SignUpRequest signUpRequest) throws IOException {
        if (usernameExist(signUpRequest.getUsername())){
            serverWriter.writeResponse(new SignUpResponse("2"));
        }else {
            if (signUpRequest.getUsername() == null || signUpRequest.getUsername().equals("")){
                serverWriter.writeResponse(new SignUpResponse("0"));
            }else if (signUpRequest.getPassword() == null || signUpRequest.getPassword().equals("")){
                serverWriter.writeResponse(new SignUpResponse("1"));
            }else{
                File file = new File("src/main/java/model/player/playerjsonfiles/" + signUpRequest.getUsername() + ".json");
                Player player = new Player(signUpRequest.getUsername(), signUpRequest.getPassword());
                player.setDefaults();
                objectMapper.writeValue(file, player);
                serverWriter.writeResponse(new SignUpResponse("3"));
            }
        }
    }

    private boolean usernameExist(String username) {
        File temp = new File(Path.PLAYER_FILE_PATH + username + ".json");
        return temp.exists();
    }
}
