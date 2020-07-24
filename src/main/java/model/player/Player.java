package model.player;

public class Player {
    private String username;
    private String password;
    private int score;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setDefaults(){
        score = 0;
    }
}
