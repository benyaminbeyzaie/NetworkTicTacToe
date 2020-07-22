package view.state;

import view.config.configmodels.ClientConfig;

import javax.swing.*;

public abstract class State extends JPanel {
    private ClientConfig config;

    State(ClientConfig clientConfig){
        this.config = clientConfig;
    }

    public ClientConfig getConfig() {
        return config;
    }

    public void setConfig(ClientConfig config) {
        this.config = config;
    }
}
