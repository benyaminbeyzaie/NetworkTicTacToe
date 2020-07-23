package network.server;

import network.client.Client;
import view.config.configmodels.ServerConfig;
import view.constants.Numbers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{
    private int port = Numbers.DEFAULT_PORT;
    private String ip = "localhost";
    private ServerSocket serverSocket;
    private ArrayList<DataInputStream> inputStreams;
    private ArrayList<DataOutputStream> outputStreams;
    private boolean serverIsReadyToTakeClient = true;

    public Server(ServerConfig config) throws IOException {
        port = config.getPort();
        ip = config.getIp();
        serverSocket = new ServerSocket(config.getPort());
        inputStreams = new ArrayList<>();
        outputStreams = new ArrayList<>();
    }

    @Override
    public void run() {
        super.run();
        listenForJoinRequest();
    }

    private void listenForJoinRequest() {
        Socket socket = null;
        try {
            while (serverIsReadyToTakeClient){
                socket = serverSocket.accept();
                inputStreams.add(new DataInputStream(socket.getInputStream()));
                outputStreams.add(new DataOutputStream(socket.getOutputStream()));
                System.out.println("CLIENT-" + inputStreams.size() + " HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
