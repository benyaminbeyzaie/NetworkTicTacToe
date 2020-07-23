package model.game;

import controller.GameController;
import model.player.Player;

import java.util.Random;

public class Game {
    private int[] board;
    private GameController controller;
    private Player currentPlayer, opponentPlayer;


    public Game(Player playerOne, Player playerTwo){
        initializePlayers(playerOne, playerTwo);
        initializeBoard();
        controller = new GameController();
    }

    private void initializePlayers(Player playerOne, Player playerTwo) {
        Random random = new Random();
        int rand = random.nextInt(2);
        if (rand == 0){
            currentPlayer = playerOne;
            opponentPlayer = playerTwo;
        }else {
            currentPlayer = playerTwo;
            opponentPlayer = playerOne;
        }
    }

    private void initializeBoard() {
        board = new int[49];
        for (int i = 0; i < 49; i++) {
                board[i] = 0;
        }
    }

    public int[] getBoard() {
        return board;
    }

    public void setBoard(int[] board) {
        this.board = board;
    }

    public void putANut(Player player, int i) {
        if (player == currentPlayer){
            if (board[i] == 0){
                board[i] = 1;
            }
        }
    }
}
