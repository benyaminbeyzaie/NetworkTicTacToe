package view.state;

import network.client.Client;
import network.request.GetAllPlayerRequest;

import java.io.IOException;

public class AllPlayerTextUpdater extends Thread {
    private Client client;

    public AllPlayerTextUpdater(Client client){
        this.client = client;
    }
    @Override
    public void run() {
        while(true){
            try {
                client.sendRequest(new GetAllPlayerRequest(client.getToken()));
                Thread.sleep(200);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
