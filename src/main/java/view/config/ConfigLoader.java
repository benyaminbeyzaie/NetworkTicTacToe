package view.config;

import view.config.configmodels.ClientConfig;
import view.config.configmodels.Config;
import view.config.configmodels.ServerConfig;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ConfigLoader {
    private static ConfigLoader configLoader;

    private ConfigLoader(){}

    public static ConfigLoader getInstance(){
        if (configLoader == null) configLoader = new ConfigLoader();
        return configLoader;
    }

    public ClientConfig loadClientConfig(String address) throws FileNotFoundException, XMLStreamException {
        FileInputStream xmlFile = new FileInputStream(address);
        return (ClientConfig)parseClientConfigXML(xmlFile);
    }

    public ServerConfig loadServerConfig(String address) throws FileNotFoundException, XMLStreamException {
        FileInputStream xmlFile = new FileInputStream(address);
        return parseServerConfigXML(xmlFile);
    }

    private ClientConfig parseClientConfigXML(FileInputStream fileInputStream) throws XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(fileInputStream);
        ClientConfig config = new ClientConfig();
        while (xmlEventReader.hasNext()){
            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            if (xmlEvent.isStartElement()){
                StartElement startElement = xmlEvent.asStartElement();
                switch (startElement.getName().getLocalPart()) {
                    case "ip":
                        xmlEvent = xmlEventReader.nextEvent();
                        config.setIp(xmlEvent.asCharacters().getData());
                        break;
                    case "port":
                        xmlEvent = xmlEventReader.nextEvent();
                        config.setPort(Integer.parseInt(xmlEvent.asCharacters().getData()));
                        break;
                    case "window_size":
                        xmlEventReader.nextTag();
                        xmlEvent = xmlEventReader.nextEvent();
                        config.setWidth(Integer.parseInt(xmlEvent.asCharacters().getData()));
                        xmlEventReader.nextTag();
                        xmlEventReader.nextTag();
                        xmlEvent = xmlEventReader.nextEvent();
                        config.setHeight(Integer.parseInt(xmlEvent.asCharacters().getData()));
                        break;
                    case "title":
                        xmlEvent = xmlEventReader.nextEvent();
                        config.setTitle(xmlEvent.asCharacters().getData());
                        break;
                }
            }
        }
        return config;
    }

    private ServerConfig parseServerConfigXML(FileInputStream fileInputStream) throws XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(fileInputStream);
        ServerConfig config = new ServerConfig();
        while (xmlEventReader.hasNext()){
            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            if (xmlEvent.isStartElement()){
                StartElement startElement = xmlEvent.asStartElement();
                switch (startElement.getName().getLocalPart()) {
                    case "ip":
                        xmlEvent = xmlEventReader.nextEvent();
                        config.setIp(xmlEvent.asCharacters().getData());
                        break;
                    case "port":
                        xmlEvent = xmlEventReader.nextEvent();
                        config.setPort(Integer.parseInt(xmlEvent.asCharacters().getData()));
                        break;
                }
            }
        }
        return config;
    }
}
