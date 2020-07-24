package network.server;

import view.config.ConfigLoader;
import view.config.configmodels.ServerConfig;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) throws IOException, XMLStreamException {
        ServerConfig serverConfig = ConfigLoader.getInstance().loadServerConfig("src/main/resources/config/server_config.xml");
        new Server(serverConfig).start();
    }
}
