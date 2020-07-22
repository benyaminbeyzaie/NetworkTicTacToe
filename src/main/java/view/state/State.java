package view.state;

import view.config.configmodels.ClientConfig;
import view.display.StateManager;

import javax.swing.*;

public abstract class State extends JPanel {
    private ClientConfig config;
    StateManager stateManager;

    State(ClientConfig clientConfig, StateManager stateManager){
        this.config = clientConfig;
        this.stateManager = stateManager;
    }

    public ClientConfig getConfig() {
        return config;
    }

    public void setConfig(ClientConfig config) {
        this.config = config;
    }
}
