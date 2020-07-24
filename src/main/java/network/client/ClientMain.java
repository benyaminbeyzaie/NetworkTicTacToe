package network.client;

import view.config.ConfigLoader;
import view.config.configmodels.ClientConfig;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException, XMLStreamException {
        ClientConfig clientConfig = ConfigLoader.getInstance().loadClientConfig("src/main/resources/config/client_config.xml");
        new Client(clientConfig).start();
    }
}
