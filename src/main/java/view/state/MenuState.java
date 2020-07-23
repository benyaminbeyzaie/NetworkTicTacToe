package view.state;

import view.config.configmodels.ClientConfig;
import view.constants.Fonts;
import view.display.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuState extends State implements StatePage {
    public MenuState(ClientConfig clientConfig, StateManager stateManager) {
        super(clientConfig, stateManager);
        setLayout(null);

        JLabel usernameLabel = new JLabel();
        usernameLabel.setText("USERNAME");
        usernameLabel.setFont(Fonts.BIG_FONT);
        usernameLabel.setBounds(15, 10, 300 , 40);

        JTextArea textArea = new JTextArea(100, 1);

        // set Top players string
        for (int i = 0; i < 1000; i++) {
            textArea.append("hello\n");
        }

        JScrollPane scrollPane = new JScrollPane(textArea);

        scrollPane.setBounds(330, 100, 150, 400);
        add(scrollPane);


        JButton backButton = new JButton();
        backButton.setBorder(null);
        backButton.setText("BACK");
        backButton.setFocusable(false);
        backButton.setForeground(Color.white);
        backButton.setBackground(Color.orange);
        backButton.setBounds(15 , 10 , 100 , 30);

        JButton startNewGameButton = new JButton();
        startNewGameButton.setBorder(null);
        startNewGameButton.setText("START A NEW GAME");
        startNewGameButton.setFocusable(false);
        startNewGameButton.setForeground(Color.BLACK);
        startNewGameButton.setBackground(Color.GREEN);
        startNewGameButton.setBounds(35 , 80 , 250 , 80);

        JButton logOutButton = new JButton();
        logOutButton.setBorder(null);
        logOutButton.setText("LOG OUT");
        logOutButton.setFocusable(false);
        logOutButton.setForeground(Color.BLACK);
        logOutButton.setBackground(Color.YELLOW);
        logOutButton.setBounds(35 , 165 , 250 , 50);

        JButton exitButton = new JButton();
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
        topPlayersLabel.setBounds(435 - getFontMetrics(Fonts.STANDARD_FONT).stringWidth(usernameLabel.getText()), 55, 300 , 40);


        add(usernameLabel);
        //add(backButton);
        add(topPlayersLabel);
        add(startNewGameButton);
        add(logOutButton);
        add(exitButton);


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
}
