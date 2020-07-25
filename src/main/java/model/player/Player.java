package model.player;

import com.fasterxml.jackson.databind.ObjectMapper;
import view.constants.Path;

import java.io.File;
import java.io.IOException;

public class Player {
    private String username;
    private String password;
    private int score;
    private int status;

    public Player(){}
    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setDefaults(){
        score = 0;
        status = 0; // offline
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean checkPassword(String password) {
        return password.equals(this.password);

    }

    public void savePlayerInfo() throws IOException {
        File file = new File(Path.PLAYER_FILE_PATH + this.getUsername() + ".json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, this);
    }
}
