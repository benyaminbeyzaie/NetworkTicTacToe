package view.assets;

import java.awt.image.BufferedImage;

public class Assets {
    public static BufferedImage TicTacTaoLogo;
    public static BufferedImage redCircle, blackCircle, redStar, blackStar;

    public static void init(){
        TicTacTaoLogo = ImageLoader.loadImage("src/main/resources/images/TicTacTaoLogo.png");
        redCircle = ImageLoader.loadImage("src/main/resources/images/redCircle.png");
        blackCircle = ImageLoader.loadImage("src/main/resources/images/blackCircle.png");
        redStar = ImageLoader.loadImage("src/main/resources/images/redStar.png");
        blackStar = ImageLoader.loadImage("src/main/resources/images/blackStar.png");
    }
}
