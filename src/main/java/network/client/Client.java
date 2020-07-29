package network.client;

import network.request.Request;
import view.assets.Assets;
import view.config.configmodels.ClientConfig;
import view.display.Display;
import view.display.StateManager;
import view.state.*;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.Socket;

public class Client extends Thread{
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Display display;
    private String token = null;
    private ClientWriter clientWriter;
    private ClientReader clientReader;
    private StateManager stateManager;

    public Client(ClientConfig config) throws IOException, XMLStreamException {
        initializeDisplay(config);
        initializeStates(config);
        try {
            socket = new Socket(config.getIp(), config.getPort());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Unable to connect to the address: " + config.getIp() + ":" + config.getPort());
        }
        clientWriter = new ClientWriter(dataOutputStream);
        clientReader = new ClientReader(dataInputStream, this);
    }

    public void sendRequest(Request request) throws IOException {
        clientWriter.sendRequest(request);
    }

    private void initializeStates(ClientConfig config) throws IOException {
        StateContainer stateContainer = new StateContainer();
        stateManager = new StateManager(display, stateContainer);
        LoginState loginState = new LoginState(config, stateManager, this);
        SignUpState signUpState = new SignUpState(config, stateManager, this);
        GameState gameState = new GameState(config, stateManager, this);
        MenuState menuState = new MenuState(config, stateManager, this);
        stateContainer.setLoginState(loginState);
        stateContainer.setSignUpState(signUpState);
        stateContainer.setGameState(gameState);
        stateContainer.setMenuState(menuState);
        stateManager.setCurrentState(loginState);
    }

    private void initializeDisplay(ClientConfig config){
        Assets.init();
        display = Display.getInstance(config);
    }

    @Override
    public void run() {
        clientReader.start();
    }

    public Display getDisplay() {
        return display;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public StateManager getStateManager() {
        return stateManager;
    }
}
