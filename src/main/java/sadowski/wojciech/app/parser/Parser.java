package sadowski.wojciech.app.parser;

import javax.xml.bind.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Parser<T> {
    private JAXBContext context;
    private JAXBIntrospector introspector;

    public Parser(Class<?>... classes) {
        setDefault(classes);
    }

    @SuppressWarnings("unchecked")
    public T getObjectFromFile(File file) {
        T object = null;
        if (file.exists()) {
            try {
                Unmarshaller unmarshaller = this.context.createUnmarshaller();
                object = (T) unmarshaller.unmarshal(file);
            } catch (JAXBException exception) {
                System.out.println("Problem occurred during unmarshalling:\n" + exception.getMessage());
            }
        }
        return object;
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
        if (introspector.isElement(object) && path.endsWith(".xml")) {
            try {
                Marshaller marshaller = context.createMarshaller();
                File file = new File(path);
                marshaller.marshal(object, file);
                file.createNewFile();
            } catch (JAXBException exception) {
                System.out.println("Problem occurred during marshalling:\n" + exception.getMessage());
            } catch (IOException exception) {
                System.out.println("Problem occurred during creating the file:\n" + exception.getMessage());
            }
        }
    }

    private void setDefault(Class<?>... classes) {
        try {
            this.context = JAXBContext.newInstance(classes);
            this.introspector = context.createJAXBIntrospector();
        } catch (JAXBException exception) {
            System.out.println("Problem occurred during context creation:\n" + exception.getMessage());
        }
    }

}
