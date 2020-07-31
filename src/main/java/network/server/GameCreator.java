package network.server;

import model.game.Game;
import network.request.NewGameRequest;

public class GameCreator extends Thread {
    private Game game;
    private String token;
    private RequestExecutor requestExecutor;

    public GameCreator(RequestExecutor requestExecutor, Game game, String token){
        this.game = game;
        this.token = token;
        this.requestExecutor = requestExecutor;
    }

    @Override
    public void run() {
        while (game == null){
            NewGameRequest request = new NewGameRequest(token);
            requestExecutor.newGame(request);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
