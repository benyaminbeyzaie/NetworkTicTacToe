package view.state;

import network.client.Client;
import network.request.LogOutRequest;
import network.request.NewGameRequest;
import view.config.configmodels.ClientConfig;
import view.constants.Fonts;
import view.display.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MenuState extends State implements StatePage {
    JLabel usernameLabel;
    JTextArea textArea;
    JButton exitButton;
    JButton logOutButton;
    JButton startNewGameButton;

    public MenuState(ClientConfig clientConfig, StateManager stateManager, Client client) throws IOException {
        super(clientConfig, stateManager,client);
        setLayout(null);

        usernameLabel = new JLabel();
        usernameLabel.setFont(Fonts.BIG_FONT);
        usernameLabel.setBounds(15, 10, 300 , 40);

        textArea = new JTextArea(10, 1);

        JScrollPane scrollPane = new JScrollPane(textArea);

        scrollPane.setBounds(330, 100, 150, 400);
        add(scrollPane);



        startNewGameButton = new JButton();
        startNewGameButton.setBorder(null);
        startNewGameButton.setText("START A NEW GAME");
        startNewGameButton.setFocusable(false);
        startNewGameButton.setForeground(Color.BLACK);
        startNewGameButton.setBackground(Color.GREEN);
        startNewGameButton.setBounds(35 , 80 , 250 , 80);
        startNewGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewGameRequest request = new NewGameRequest(client.getToken());
                try {
                    client.sendRequest(request);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        logOutButton = new JButton();
        logOutButton.setBorder(null);
        logOutButton.setText("LOG OUT");
        logOutButton.setFocusable(false);
        logOutButton.setForeground(Color.BLACK);
        logOutButton.setBackground(Color.YELLOW);
        logOutButton.setBounds(35 , 165 , 250 , 50);
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.sendRequest(new LogOutRequest(client.getToken()));
                    stateManager.setCurrentState(stateManager.getStateContainer().getLoginState());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                client.setToken(null);
            }
        });

        exitButton = new JButton();
        exitButton.setBorder(null);
        exitButton.setText("EXIT");
        exitButton.setFocusable(false);
        exitButton.setForeground(Color.BLACK);
        exitButton.setBackground(Color.RED);
        exitButton.setBounds(35 , 220 , 250 , 50);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JLabel topPlayersLabel = new JLabel();
        topPlayersLabel.setText("TOP PLAYERS");
        topPlayersLabel.setFont(Fonts.STANDARD_FONT);
        topPlayersLabel.setBounds(350 - getFontMetrics(Fonts.STANDARD_FONT).stringWidth(usernameLabel.getText()), 55, 300 , 40);


        add(usernameLabel);
        add(topPlayersLabel);
        add(startNewGameButton);
        add(logOutButton);
        add(exitButton);


    }

    public void setVisibility(boolean b){
     startNewGameButton.setVisible(b);
     logOutButton.setVisible(b);
     exitButton.setVisible(b);
     usernameLabel.setText("Waiting for player... ");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0 , 60, 500, 2);
        g.fillRect(320, 60, 2, 600);

    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

    public void setUsernameLabel(JLabel usernameLabel) {
        this.usernameLabel = usernameLabel;
    }
}
