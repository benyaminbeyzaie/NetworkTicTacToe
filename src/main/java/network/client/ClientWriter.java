package network.client;

import network.request.Request;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

class ClientWriter {
    private PrintStream printStream;
    ClientWriter(DataOutputStream dataOutputStream){
        printStream = new PrintStream(dataOutputStream);
    }
    void sendRequest(Request request) throws IOException {
        printStream.println(request.toString());
        printStream.flush();
        System.out.println("Request: " + request + " is send via output stream");
    }
}
