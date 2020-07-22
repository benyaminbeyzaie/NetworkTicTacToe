package view.utils;

import view.constants.Numbers;

import javax.swing.*;
import java.awt.*;

public class OnBoardButton extends JButton {
    public OnBoardButton(int x, int y){
        setBounds(x, y, Numbers.ON_BOARD_BUTTON_SIZE, Numbers.ON_BOARD_BUTTON_SIZE);
        setBackground(Color.ORANGE);
        setFocusable(false);
        setBorder(null);
    }


}
