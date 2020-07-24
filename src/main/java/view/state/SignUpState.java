package view.state;

import network.client.Client;
import network.request.SignUpRequest;
import view.config.configmodels.ClientConfig;
import view.constants.Fonts;
import view.display.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SignUpState extends State implements StatePage {
    public SignUpState(ClientConfig clientConfig, StateManager stateManager, Client client) {
        super(clientConfig, stateManager, client);
        setLayout(null);
        // Login label
        JLabel loginLabel = new JLabel("Sign up");
        loginLabel.setFont(Fonts.BIG_FONT);
        loginLabel.setBounds( 3 * super.getConfig().getWidth() / 4 - (getFontMetrics(Fonts.BIG_FONT).stringWidth(loginLabel.getText()) / 2 + 5), 20,
                100, 100);


        //Username text field
        JTextField usernameField = new JTextField(18);
        usernameField.setBounds(3 * super.getConfig().getWidth() / 4 - 105, 200 ,200, 30);

        //Password text field
        JTextField passwordField = new JTextField(18);
        passwordField.setBounds(3 * super.getConfig().getWidth() / 4 - 105, 240 ,200, 30);

        //Login button
        JButton loginButton = new JButton();
        loginButton.setText("Sign up");
        loginButton.setBounds(3 * super.getConfig().getWidth() / 4 - 105, 280, 200, 30);
        loginButton.setBackground(Color.GREEN);
        loginButton.setBorder(null);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // sign up request
                SignUpRequest signUpRequest = new SignUpRequest(usernameField.getText(), passwordField.getText());
                try {
                    client.sendRequest(signUpRequest);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //Sign up button
        JButton signUpButton = new JButton();
        signUpButton.setText("Back");
        signUpButton.setBounds(3 * super.getConfig().getWidth() / 4 - 200 + 95, 320, 95, 30);
        signUpButton.setBackground(Color.ORANGE);
        signUpButton.setBorder(null);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // set content page to sign up state
                stateManager.setCurrentState(stateManager.getStateContainer().getLoginState());
            }
        });

        add(loginLabel);
        add(usernameField);
        add(passwordField);
        add(loginButton);
        add(signUpButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    @Override
    public void render(Graphics g) {
        drawLoginStateBackground(g);
    }
}
