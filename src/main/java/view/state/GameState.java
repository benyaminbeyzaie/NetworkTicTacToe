package view.state;

import model.game.Game;
import model.player.Player;
import view.assets.Assets;
import view.config.configmodels.ClientConfig;
import view.constants.Fonts;
import view.constants.Numbers;
import view.display.StateManager;
import view.utils.OnBoardButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameState extends State implements StatePage{
    private Game game;
    private Player signedPlayer;
    private ArrayList<JButton> buttons;
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
        buttons = new ArrayList<>();
        for (int i = 0; i < 49; i++){
            OnBoardButton button = new OnBoardButton(i / 7 + 15 + (i / 7 * (5 + Numbers.ON_BOARD_BUTTON_SIZE)), i % 7 + 100 + (Numbers.ON_BOARD_BUTTON_SIZE + 5) * (i % 7));
            //button.setIcon(new ImageIcon(Assets.redStar));
            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.putANut(signedPlayer, finalI);
                    revalidate();
                }
            });
            buttons.add(button);
            add(button);
        }
    }

    @Override
    public void render(Graphics g) {
        for (int i = 0; i < 49; i++) {
            if (game.getBoard()[i] == 1){
                buttons.get(i).setIcon(new ImageIcon(Assets.redStar));
            }else if (game.getBoard()[i] == 2){
                buttons.get(i).setIcon(new ImageIcon(Assets.blackCircle));
            }
        }
    }

    public Player getSignedPlayer() {
        return signedPlayer;
    }

    public void setSignedPlayer(Player signedPlayer) {
        this.signedPlayer = signedPlayer;
    }
}
