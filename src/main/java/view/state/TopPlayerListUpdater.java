package view.state;

import network.client.Client;
import network.request.GetAllPlayersNameWithStatusRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TopPlayerListUpdater extends Thread {
    MenuState menuState;
    Client client;
    TopPlayerListUpdater(MenuState menuState, Client client){
        this.menuState = menuState;
        this.client = client;
    }
    @Override
    public void run() {
        while (!isInterrupted()){
            GetAllPlayersNameWithStatusRequest getAllPlayersNameWithStatusRequest = new GetAllPlayersNameWithStatusRequest(
                    client.getToken()
            );
            String players = null;
            try {
                players = client.sendRequest(getAllPlayersNameWithStatusRequest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert players != null;
            ArrayList<String> list = new ArrayList<String>(Arrays.asList(players.split("-")));
            menuState.getTextArea().setText(null);
            for (String s :
                    list) {
                if (s.charAt(s.length() - 1) == '0'){
                    String temp = s;
                    String tempTwo = s;
                    s = "(offline) " + temp.substring(0 , temp.indexOf("/")) + ": " + tempTwo.substring(tempTwo.indexOf("/") + 1, tempTwo.length() - 1);
                }else if (s.charAt(s.length() - 1) == '1'){
                    String temp = s;
                    String tempTwo = s;
                    s = "(online) " + temp.substring(0 , temp.indexOf("/")) + ": " + tempTwo.substring(tempTwo.indexOf("/") + 1, tempTwo.length() - 1);
                }
                menuState.getTextArea().append(s);
                menuState.getTextArea().append("\n");
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
