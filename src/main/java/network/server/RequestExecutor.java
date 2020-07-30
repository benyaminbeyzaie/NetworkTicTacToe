package network.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.player.Player;
import network.request.*;
import network.response.GetAllPlayerResponse;
import network.response.GetSignedPlayerUsernameResponse;
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
        if (request.getType().equals("Username")){
            GetSignedPlayerUsernameRequest getSignedPlayerUsernameRequest = objectMapper.readValue(jsonRequest, GetSignedPlayerUsernameRequest.class);
            username(getSignedPlayerUsernameRequest);
        }
        if (request.getType().equals("AllPlayer")){
            GetAllPlayerRequest getAllPlayerRequest = objectMapper.readValue(jsonRequest, GetAllPlayerRequest.class);
            allPlayer(getAllPlayerRequest);
        }
        if (request.getType().equals("LogOut")){
            LogOutRequest logOutRequest = objectMapper.readValue(jsonRequest, LogOutRequest.class);
            logOut(logOutRequest);
        }
    }

    private void logOut(LogOutRequest logOutRequest) {
        if (logOutRequest.getToken().equals(clientHandler.getToken())){
            clientHandler.getSignedPlayer().setStatus(0);
            clientHandler.setSignedPlayer(null);
        }
    }

    private void allPlayer(GetAllPlayerRequest getAllPlayerRequest) {
        if (getAllPlayerRequest.getToken().equals(clientHandler.getToken())){
            StringBuilder stringBuilder = new StringBuilder();
            for (Player player :
                 server.getAllPlayers()) {
                String status = (player.getStatus() == 1) ? "online" : "offline";
                stringBuilder.append(player.getUsername());
                stringBuilder.append(": ");
                stringBuilder.append(status);
                stringBuilder.append(": ");
                stringBuilder.append(player.getScore());
                stringBuilder.append("/");
            }
            GetAllPlayerResponse getAllPlayerResponse = new GetAllPlayerResponse(stringBuilder.toString());
            serverWriter.writeResponse(getAllPlayerResponse);
        }
    }

    private void username(GetSignedPlayerUsernameRequest getSignedPlayerUsernameRequest) {
        if (getSignedPlayerUsernameRequest.getToken().equals(clientHandler.getToken())){
            serverWriter.writeResponse(new GetSignedPlayerUsernameResponse(clientHandler.getSignedPlayer().getUsername()));
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
                        for (int i = 0; i < server.getAllPlayers().size(); i++) {
                            if (server.getAllPlayers().get(i).getUsername().equals(playerToSign.getUsername())){
                                clientHandler.setSignedPlayer(server.getAllPlayers().get(i));
                                break;
                            }
                        }
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
