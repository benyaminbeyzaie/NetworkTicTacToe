package network.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import network.request.GetSignedPlayerUsernameRequest;
import network.response.*;
import network.server.GameCreator;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ClientReader extends Thread {
    private Scanner scanner;
    private ObjectMapper objectMapper;
    private Client client;

    ClientReader(DataInputStream dataInputStream, Client client) {
        scanner = new Scanner(dataInputStream);
        this.client = client;
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void run() {
        super.run();
        while (!interrupted()) {
            try {
                executeResponse(scanner.nextLine());
                Thread.sleep(200);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void executeResponse(String jsonResponse) throws IOException {
        System.out.println("Response: " + jsonResponse);
        Response response = objectMapper.readValue(jsonResponse, Response.class);
        if (response.getType().equals("SignUp")) {
            SignUpResponse signUpResponse = objectMapper.readValue(jsonResponse, SignUpResponse.class);
            client.getStateManager().getStateContainer().getSignUpState().showDialogBox(signUpResponse.getStatus());
        }
        if (response.getType().equals("Login")) {
            LoginResponse loginResponse = objectMapper.readValue(jsonResponse, LoginResponse.class);
            client.getStateManager().getStateContainer().getLoginState().showDialogBox(loginResponse.getStatus());
        }
        if (response.getType().equals("Username")) {
            GetSignedPlayerUsernameResponse getSignedPlayerUsernameResponse = objectMapper.readValue(jsonResponse, GetSignedPlayerUsernameResponse.class);
            client.getStateManager().getStateContainer().getMenuState().getUsernameLabel().setText(getSignedPlayerUsernameResponse.getUsername());
        }
        if (response.getType().equals("AllPlayer")) {
            GetAllPlayerResponse getAllPlayerResponse = objectMapper.readValue(jsonResponse, GetAllPlayerResponse.class);
            String[] names = getAllPlayerResponse.getAllPlayersByDash().split("/");
            StringBuilder namesInTextArea = new StringBuilder();
            String[] sortedName = sortNames(names);
            for (String name : sortedName) {
                namesInTextArea.append(name);
                namesInTextArea.append("\n");
            }
            client.getStateManager().getStateContainer().getMenuState().getTextArea().setText(namesInTextArea.toString());
        }
        if (response.getType().equals("NewGame")){
            NewGameResponse newGameResponse = objectMapper.readValue(jsonResponse, NewGameResponse.class);
            client.getStateManager().getStateContainer().getMenuState().setVisibility(false);
            if (newGameResponse.getResult() == 0) {
                // a active game  here
            }
            if (newGameResponse.getResult() == 1){
                // player is in queue start a new thread
                client.getStateManager().getStateContainer().getMenuState().setVisibility(false);
            }
            if (newGameResponse.getResult() == 2){
                // start a game
                client.getStateManager().setCurrentState(client.getStateManager().getStateContainer().getGameState());
            }
        }
    }

    private String[] sortNames(String[] names) {
        Map<Integer, Integer> indexToScore = new TreeMap<>();
        for (int i = 0; i < names.length; i++) {
            int begin = names[i].length() - 1;
            while (names[i].charAt(begin) >= '0' && names[i].charAt(begin) <= '9') {
                begin--;
            }
            indexToScore.put(i, Integer.parseInt(names[i].substring(begin + 1)));
            indexToScore = sortByValue(indexToScore);
        }
        int i = names.length - 1;
        String[] sorted = new String[names.length];
        for (Map.Entry<Integer, Integer> entry : indexToScore.entrySet()) {
            sorted[i] = names[entry.getKey()];
            i--;
        }
        return sorted;
    }

    private Map<Integer, Integer> sortByValue(final Map<Integer, Integer> wordCounts) {
        return wordCounts.entrySet()
                .stream()
                .sorted((Map.Entry.<Integer, Integer>comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
