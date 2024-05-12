package sadowski.wojciech.app.person;

import sadowski.wojciech.app.person.type.Type;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class PersonService {
    private final PersonRepository repository;

    public PersonService() {
        this.repository = new PersonRepository();
    }

    public Person getByPersonId(String personId) {
        Person person = repository.getByIdFromInternal(personId);
        if(person == null) {
            person = repository.getByIdFromExternal(personId);
            if(person != null) {
                person.setType(Type.external);
            }
        } else {
            person.setType(Type.internal);
        }
        return person;
    }

    public ArrayList<Person> getAll() {
        ArrayList<Person> allPeople = new ArrayList<>();
        ArrayList<Person> internalPeople = repository.getAllFromInternal();
        ArrayList<Person> externalPeople = repository.getAllFromExternal();
        if(internalPeople != null) {
            allPeople.addAll(repository.getAllFromInternal().stream().peek(person -> person.setType(Type.internal)).collect(Collectors.toList()));
        }
        if(externalPeople != null) {
            allPeople.addAll(repository.getAllFromExternal().stream().peek(person -> person.setType(Type.external)).collect(Collectors.toList()));
        }
        if(allPeople.isEmpty()) allPeople = null;
        return allPeople;
    }

    public ArrayList<Person> getByType(Type type) {
        ArrayList<Person> people = null;
        if(type.equals(Type.internal)) {
            people = repository.getAllFromInternal();
            if(people == null) {
                return null;
            }
            people.forEach(person -> person.setType(Type.internal));
        } else if(type.equals(Type.external)) {
            people = repository.getAllFromExternal();
            if(people == null) {
                return null;
            }
            people.forEach(person -> person.setType(Type.external));
        }
        return people;
    }

    public ArrayList<Person> getByFirstName(String firstName) {
        return  getAll()
                .stream()
                .filter(person -> person.getFirstName().equals(firstName))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Person> getByLastName(String lastName) {
        return getAll()
                .stream()
                .filter(person -> person.getLastName().equals(lastName))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Person getByMobile(String mobile) {
        return getAll()
                .stream()
                .filter(person -> person.getMobile().equals(mobile))
                .findFirst()
                .orElse(null);
    }

    public Person getByEmail(String email) {
        return getAll()
                .stream()
                .filter(person -> person.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public Person getByPesel(String pesel) {
        return getAll()
                .stream()
                .filter(person -> person.getPesel().equals(pesel))
                .findFirst()
                .orElse(null);
    }

    public ArrayList<Person> findToList(String personId, String firstName, String lastName, String mobile, String email, String pesel, Type type) {
        return getAll()
                .stream()
                .filter(person -> personId == null || Objects.equals(personId, person.getPersonId()))
                .filter(person -> firstName == null || Objects.equals(firstName, person.getFirstName()))
                .filter(person -> lastName == null || Objects.equals(lastName, person.getLastName()))
                .filter(person -> mobile == null || Objects.equals(mobile, person.getMobile()))
                .filter(person -> email == null || Objects.equals(email, person.getEmail()))
                .filter(person -> pesel == null || Objects.equals(pesel, person.getPesel()))
                .filter(person -> type == null || Objects.equals(type, person.getType()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Person find(String personId, String firstName, String lastName, String mobile, String email, String pesel, Type type) {
        ArrayList<Person> people = findToList(personId, firstName, lastName, mobile, email, pesel, type);
        if(people.isEmpty()) {
            return null;
        } else {
            return people.get(0);
        }
    }

    public void create(Person person) {
        repository.create(person);
    }

    public void modify(Person person) {
        repository.modify(person);
    }

    public boolean remove(String personId) {
        return repository.remove(personId);
    }

}
