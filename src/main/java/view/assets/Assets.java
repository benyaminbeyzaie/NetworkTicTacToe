package view.assets;

import java.awt.image.BufferedImage;

public class Assets {
    public static BufferedImage TicTacTaoLogo;

    public static void init(){
        TicTacTaoLogo = ImageLoader.loadImage("src/main/resources/images/TicTacTaoLogo.png");
    }
}
