package network.server;

import network.response.Response;

import java.io.OutputStream;
import java.io.PrintStream;

public class ServerWriter {
    private PrintStream printStream;

    ServerWriter(OutputStream outputStream){
        printStream = new PrintStream(outputStream);
    }

    void writeResponse(Response response){
        printStream.println(response.toString());
        printStream.flush();
    }
}
