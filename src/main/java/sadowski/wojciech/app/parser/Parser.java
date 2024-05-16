package sadowski.wojciech.app.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import sadowski.wojciech.app.parser.handler.XMLHandler;
import sadowski.wojciech.app.annotation.XMLElement;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class Parser<T> {
    private final XMLHandler handler;
    private SAXParser parser;
    private DocumentBuilder builder;

    public Parser(XMLHandler handler) {
        this.handler = handler;
        try {
            this.parser = SAXParserFactory.newInstance().newSAXParser();
            this.builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException exception) {
            System.out.println("Problem occurred during instance creation with the requested configuration:\n" + exception.getMessage());
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
        Document document = generateDocument(object);
        try (FileOutputStream output = new FileOutputStream(path)) {
            writeXml(document, output);
        } catch (IOException exception) {
            System.out.println("Error occurred during file creation:\n" + exception.getMessage());
        }
    }

    private Document generateDocument(T object) {
        Document document = builder.newDocument();
        Element rootElement = document.createElement(object.getClass().getSimpleName().toLowerCase());
        document.appendChild(rootElement);
        getFieldsAndValues(object).forEach((name, value) -> {
            Element element = document.createElement(name);
            element.setTextContent(value);
            rootElement.appendChild(element);
        });
        return document;
    }

    private void writeXml(Document document, OutputStream output) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(output);
            transformer.transform(source, result);
        } catch (TransformerConfigurationException exception) {
            System.out.println("Problem occurred during Transformer instance creation:\n" + exception.getMessage());
        } catch (TransformerException exception) {
            System.out.println("Unrecoverable error occurs during the course of the transformation\n" + exception.getMessage());
        }
    }

    private HashMap<String, String> getFieldsAndValues(T object) {
        Field[] fields = object.getClass().getDeclaredFields();
        HashMap<String, String> map = new HashMap<>();
        for (Field field : fields) {
            if(field.isAnnotationPresent(XMLElement.class)) {
                field.setAccessible(true);
                try {
                    String value = field.get(object).toString();
                    map.put(field.getName(), value);
                } catch (IllegalAccessException exception) {
                    System.out.println("Error accessing the filed:\n" + exception.getMessage());
                }
            }
        }
        return map;
    }

}
