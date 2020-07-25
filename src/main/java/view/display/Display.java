package view.display;

import view.config.configmodels.ClientConfig;
import view.state.State;

import javax.swing.*;

public class Display extends JFrame {
    private static Display display;

    private Display(ClientConfig clientConfig){
        super();
        setTitle("Tic-Tac-Toe");
        setSize(clientConfig.getWidth(), clientConfig.getHeight());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public static Display getInstance(ClientConfig clientConfig){
        if (display == null) display = new Display(clientConfig);
        return display;
    }

    public void setCurrentState(State state){
        setContentPane(state);
    }

}
