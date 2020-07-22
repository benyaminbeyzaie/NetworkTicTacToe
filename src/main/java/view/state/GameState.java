package view.state;

import view.config.configmodels.ClientConfig;
import view.constants.Fonts;
import view.constants.Numbers;
import view.display.StateManager;
import view.utils.OnBoardButton;

import javax.swing.*;
import java.awt.*;

public class GameState extends State implements StatePage{
    public GameState(ClientConfig clientConfig, StateManager stateManager) {
        super(clientConfig, stateManager);
        setLayout(null);

        JLabel turnLabel = new JLabel();
        turnLabel.setText("YOUR TURN");
        turnLabel.setFont(Fonts.BIG_FONT);
        turnLabel.setBounds(450 - getFontMetrics(Fonts.BIG_FONT).stringWidth(turnLabel.getText()), 20, 300 , 40);

        JButton exitGameButton = new JButton();
        exitGameButton.setBorder(null);
        exitGameButton.setText("EXIT GAME");
        exitGameButton.setFocusable(false);
        exitGameButton.setForeground(Color.white);
        exitGameButton.setBackground(Color.RED);
        exitGameButton.setBounds(15 , 15 , 100 , 50);

        addGamePanelButtons();


        add(exitGameButton);
        add(turnLabel);
    }

    private void addGamePanelButtons() {
        for (int i = 0; i < 49; i++){
            OnBoardButton button = new OnBoardButton(i / 7 + 15 + (i / 7 * (5 + Numbers.ON_BOARD_BUTTON_SIZE)), i % 7 + 100 + (Numbers.ON_BOARD_BUTTON_SIZE + 5) * (i % 7));
            add(button);
        }
    }

    @Override
    public void render(Graphics g) {

    }
}
