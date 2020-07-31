package network.client;

import network.client.Client;
import network.request.GetAllPlayerRequest;
import network.server.ClientHandler;
import network.server.RequestExecutor;

import java.io.IOException;

public class AllPlayerTextUpdater extends Thread {
    private RequestExecutor requestExecutor;
    private String token;
    private GetAllPlayerRequest getAllPlayerRequest;

    public AllPlayerTextUpdater(RequestExecutor requestExecutor, String token){
        this.requestExecutor = requestExecutor;
        this.token = token;
        this.getAllPlayerRequest = new GetAllPlayerRequest(token);
    }
    @Override
    public void run() {
        while(true){
            try {
                requestExecutor.executeRequest(getAllPlayerRequest.toString());
                Thread.sleep(1000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
