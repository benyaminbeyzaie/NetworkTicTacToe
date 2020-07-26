package network.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.player.Player;
import view.constants.Path;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayerStatusChecker extends Thread {
    private ArrayList<ClientHandler> clientHandlers;
    private ArrayList<Player> allPlayers;

    PlayerStatusChecker(ArrayList<ClientHandler> clientHandlers, ArrayList<Player> allPlayers) {
        this.clientHandlers = clientHandlers;
        this.allPlayers = allPlayers;
    }

    @Override
    public synchronized void run() {
        super.run();
        for (Player player :
                allPlayers) {
            System.out.println("====");
            player.setStatus(0);
            try {
                player.savePlayerInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        while (true){
            synchronized (allPlayers){
                for (ClientHandler clientHandler :
                        clientHandlers) {
                    if (clientHandler.isAlive()){
                        System.out.println("alive");
                        if (clientHandler.getSignedPlayer() != null){
                            clientHandler.getSignedPlayer().setStatus(1);
                        }
                    }else{
                        for (Player player :
                                allPlayers) {
                            System.out.println("====");
                            player.setStatus(0);
                            try {
                                player.savePlayerInfo();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        for (ClientHandler c :
                                clientHandlers) {
                            if (c.isAlive()) {
                                System.out.println("alive");
                                if (c.getSignedPlayer() != null) {
                                    c.getSignedPlayer().setStatus(1);
                                }
                            }
                        }
                    }
                }
                ArrayList<ClientHandler> toRemove = new ArrayList<>();
                for (ClientHandler c :
                        clientHandlers) {
                    if (!c.isAlive()) {
                        toRemove.add(c);
                    }
                }
                for (ClientHandler c :
                        toRemove) {
                    clientHandlers.remove(c);
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
