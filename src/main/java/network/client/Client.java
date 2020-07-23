package network.client;

import view.assets.Assets;
import view.config.ConfigLoader;
import view.config.configmodels.ClientConfig;
import view.display.Display;
import view.display.StateManager;
import view.state.*;

import javax.xml.stream.XMLStreamException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread{
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Display display;
    private boolean isConnectedToTheServer;

    public Client(ClientConfig config) throws IOException, XMLStreamException {
        initializeDisplay(config);
        initializeStates(config);
        try {
            socket = new Socket(config.getIp(), config.getPort());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            isConnectedToTheServer = true;
        } catch (IOException e) {
            System.out.println("Unable to connect to the address: " + config.getIp() + ":" + config.getPort());
            isConnectedToTheServer = false;
        }
    }

    private void initializeStates(ClientConfig config) {
        StateContainer stateContainer = new StateContainer();
        StateManager stateManager = new StateManager(display, stateContainer);

        LoginState loginState = new LoginState(config, stateManager);
        SignUpState signUpState = new SignUpState(config, stateManager);
        GameState gameState = new GameState(config, stateManager);
        MenuState menuState = new MenuState(config, stateManager);
        stateContainer.setLoginState(loginState);
        stateContainer.setSignUpState(signUpState);
        stateContainer.setGameState(gameState);
        stateContainer.setMenuState(menuState);

        stateManager.setCurrentState(loginState);
    }

    private void initializeDisplay(ClientConfig config) throws FileNotFoundException, XMLStreamException {
        Assets.init();
        ClientConfig clientConfig = ConfigLoader.getInstance().
                loadClientConfig("src/main/resources/config/client_config.xml");
        display = Display.getInstance(clientConfig);
    }

    @Override
    public void run() {
        super.run();
    }
}
