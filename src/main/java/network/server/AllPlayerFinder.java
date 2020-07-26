package network.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.player.Player;
import view.constants.Path;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AllPlayerFinder extends Thread {
    ArrayList<Player> allPlayers;

    AllPlayerFinder(ArrayList<Player> allPlayers){
        this.allPlayers = allPlayers;
    }

    @Override
    public void run() {
        super.run();
        ObjectMapper objectMapper = new ObjectMapper();
        while (true){
            synchronized (allPlayers){
                File[] playerFiles = new File(Path.PLAYER_FILE_PATH).listFiles();
                ArrayList<File> playerFilesList = new ArrayList<>(Arrays.asList(playerFiles));
                if (allPlayers.size() > playerFiles.length) allPlayers.removeAll(allPlayers);
                for(File file : playerFilesList) {
                    Player player = null;
                    try {
                        player = objectMapper.readValue(file, Player.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (!player.isIn(allPlayers)){
                        allPlayers.add(player);
                    }
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
