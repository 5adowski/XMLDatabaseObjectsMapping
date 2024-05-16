package sadowski.wojciech.app.parser;

import org.xml.sax.SAXException;
import sadowski.wojciech.app.parser.handler.XMLHandler;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Parser<T> {
    private SAXParser parser;
    private final XMLHandler handler;

    public Parser(XMLHandler handler) {
        this.handler = handler;
        try {
            this.parser = SAXParserFactory.newInstance().newSAXParser();
        } catch (ParserConfigurationException exception) {
            System.out.println("Problem occurred during SAXParser instance creation with the requested configuration:\n" + exception.getMessage());
        } catch (SAXException exception) {
            System.out.println("Problem occurred during initialization of the underlying parser:\n" + exception.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public T getObjectFromFile(File file) {
        if(file.exists()) {
            try {
                parser.parse(file.getPath(), handler);
            } catch (SAXException exception) {
                System.out.println("Problem occurred during parsing the given XML content:\n" + exception.getMessage());
            } catch (IOException exception) {
                System.out.println("Problem occurred during reading the given input stream:\n" + exception.getMessage());
            }
            return (T) handler.getObject();
        } else {
            return null;
        }
    }

    public ArrayList<T> getObjectsFromDirectory(String path) {
        ArrayList<T> objects = null;
        File directory = new File(path);
        File[] files = directory.listFiles();
        if(files != null && files.length > 0) {
            objects = new ArrayList<>(files.length);
            for(File file : files) {
                objects.add(getObjectFromFile(file));
            }
        }
        return objects;
    }

    public void writeObjectToFile(T object, String path) {

    }

}
