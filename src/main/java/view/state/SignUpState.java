package view.state;

import view.assets.Assets;
import view.config.configmodels.ClientConfig;
import view.constants.Fonts;
import view.display.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpState extends State implements StatePage {
    public SignUpState(ClientConfig clientConfig, StateManager stateManager) {
        super(clientConfig, stateManager);
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
                // login request
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
                stateManager.setCurrentState(stateManager.getStateHandler().getLoginState());
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
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, super.getWidth()/2, super.getHeight());
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.drawImage(Assets.TicTacTaoLogo, super.getWidth() / 4 - Assets.TicTacTaoLogo.getWidth() / 2,
                super.getHeight() / 2 - Assets.TicTacTaoLogo.getHeight() / 2, null);
    }
}
