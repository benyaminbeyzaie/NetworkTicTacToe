package view.state;

import view.assets.Assets;
import view.config.configmodels.ClientConfig;
import view.display.StateManager;

import javax.swing.*;
import java.awt.*;

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

    void drawLoginStateBackground(Graphics g){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, super.getWidth()/2, super.getHeight());
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.drawImage(Assets.TicTacTaoLogo, super.getWidth() / 4 - Assets.TicTacTaoLogo.getWidth() / 2,
                super.getHeight() / 2 - Assets.TicTacTaoLogo.getHeight() / 2, null);
    }
}
