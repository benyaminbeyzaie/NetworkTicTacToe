package network.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import network.request.Request;

import java.io.InputStream;
import java.util.Scanner;

public class ServerReader {
    private Scanner scanner;

    ServerReader(InputStream inputStream){
        scanner = new Scanner(inputStream);
    }

    public String read() {
            return scanner.nextLine();
    }
}
