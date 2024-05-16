package sadowski.wojciech.app.person;

import sadowski.wojciech.app.configuration.DatabaseConfiguration;
import sadowski.wojciech.app.parser.Parser;
import sadowski.wojciech.app.person.type.Type;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PersonRepository {
    private final Parser<Person> parser;

    public PersonRepository() {
        this.parser = new Parser<>(new PersonHandler());
    }

    public Person getByIdFromInternal(String personId) {
        return parser.getObjectFromFile(new File(String.format("%s%s/%s.xml", DatabaseConfiguration.PEOPLE_PATH_CATALOG, Type.internal, personId)));
    }

    public Person getByIdFromExternal(String personId) {
        return parser.getObjectFromFile(new File(String.format("%s%s/%s.xml", DatabaseConfiguration.PEOPLE_PATH_CATALOG, Type.external, personId)));
    }

    public ArrayList<Person> getAllFromInternal() {
        return parser.getObjectsFromDirectory(String.format("%s%s/", DatabaseConfiguration.PEOPLE_PATH_CATALOG, Type.internal));
    }

    public ArrayList<Person> getAllFromExternal() {
        return parser.getObjectsFromDirectory(String.format("%s%s/", DatabaseConfiguration.PEOPLE_PATH_CATALOG, Type.external));
    }

    public void create(Person person) {
        parser.writeObjectToFile(person, String.format("%s%s/%s.xml", DatabaseConfiguration.PEOPLE_PATH_CATALOG, person.getType(), person.getPersonId()));
    }

    public void create(List<Person> people) {
        people.forEach(this::create);
    }

    public void modify(Person person) {
        create(person);
    }

    public void modify(List<Person> people) {
        create(people);
    }

    public boolean remove(String personId) {
        if(new File(String.format("%s%s/%s.xml", DatabaseConfiguration.PEOPLE_PATH_CATALOG, Type.internal, personId)).delete()) {
            return true;
        } else {
            return new File(String.format("%s%s/%s.xml", DatabaseConfiguration.PEOPLE_PATH_CATALOG, Type.external, personId)).delete();
        }
    }

}
