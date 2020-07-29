package view.state;


import network.client.Client;
import network.request.LoginRequest;
import view.config.configmodels.ClientConfig;
import view.constants.Fonts;
import view.display.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginState extends State implements StatePage{

    public LoginState(ClientConfig config, StateManager stateManager, Client client){
        super(config, stateManager, client);
        setLayout(null);

        // Login label
        JLabel loginLabel = new JLabel("Login");
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
        loginButton.setText("Login");
        loginButton.setBounds(3 * super.getConfig().getWidth() / 4 - 105, 280, 200, 30);
        loginButton.setBackground(Color.ORANGE);
        loginButton.setBorder(null);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // login request
                LoginRequest loginRequest = new LoginRequest(usernameField.getText(), passwordField.getText());
                try {
                    client.sendRequest(loginRequest);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //Sign up button
        JButton signUpButton = new JButton();
        signUpButton.setText("Sign up");
        signUpButton.setBounds(3 * super.getConfig().getWidth() / 4 - 200 + 95, 320, 95, 30);
        signUpButton.setBackground(Color.GREEN);
        signUpButton.setBorder(null);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // set content page to sign up state
                try {
                    stateManager.setCurrentState(stateManager.getStateContainer().getSignUpState());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //Exit button
        JButton exitButton = new JButton();
        exitButton.setText("Exit");
        exitButton.setBounds(3 * super.getConfig().getWidth() / 4 + 95 + 10 - 200 + 95, 320, 95, 30);
        exitButton.setBackground(Color.RED);
        exitButton.setBorder(null);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        add(loginLabel);
        add(usernameField);
        add(passwordField);
        add(loginButton);
        add(signUpButton);
        add(exitButton);
    }

    public void showDialogBox(String out) throws IOException {
        String message = "successful!";
        switch (out){
            case "0" : message = "username can not be empty";
                break;
            case "1" : message = "password can not be empty";
                break;
            case "2" : message = "username is not existed";
                break;
            case "3" : message = "password is incorrect";
                break;
            case "4" : message = "player is already online";
        }
        if (out.length() > 1) {
            client.setToken(out);
            client.getStateManager().setCurrentState(client.getStateManager().getStateContainer().getMenuState());
        }else {
            JOptionPane.showMessageDialog(client.getDisplay(),
                    message);
        }
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
