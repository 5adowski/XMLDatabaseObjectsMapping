package sadowski.wojciech.app.person;

import sadowski.wojciech.app.person.type.Type;

import java.util.List;
import java.util.Objects;

public class PersonServiceTest {
    private static final PersonService SERVICE = new PersonService();
    private static final Person TEST_PERSON = new Person("1123331", "Jan", "Kowalski", "501554332", "jan.kowalski@mail.com", "8910145862", Type.internal);

    public static void main(String[] args) {
        // setup database with test data
        // given
        SERVICE.create(TEST_PERSON);

        // tests
        shouldFindById();
        shouldNotFindByNotExistingId();
        shouldFindByMobileAndEmail();
        shouldNotFindByMobileAndNotExistingEmail();
        shouldFindByType();
        shouldNotFindByType();
        shouldFindByFirstName();
        shouldFindByLastName();
        shouldNotFindByNotExistingLastName();
        shouldFindByEmail();
        shouldFindByMobile();
        shouldFindByPesel();
        shouldNotFindByNotExistingPesel();
        shouldCreatePerson();
        shouldModifyPerson();
        shouldRemovePerson();

        // clean database
        SERVICE.remove(TEST_PERSON.getPersonId());
    }

    private static void shouldFindById() {
        // when
        Person receivedPerson = SERVICE.getByPersonId(TEST_PERSON.getPersonId());
        // then
        assert Objects.equals(TEST_PERSON, receivedPerson) : testFailed(TEST_PERSON, receivedPerson);
        testPassed();
    }

    private static void shouldNotFindByNotExistingId() {
        // when
        Person receivedPerson = SERVICE.getByPersonId("666");
        // then
        assert receivedPerson == null : testFailed(null, receivedPerson);
        testPassed();
    }

    private static void shouldFindByMobileAndEmail() {
        // when
        Person receivedPerson = SERVICE.find(null, null, null, "501554332", "jan.kowalski@mail.com", null, null);
        // then
        assert Objects.equals(TEST_PERSON, receivedPerson) : testFailed(TEST_PERSON, receivedPerson);
        testPassed();
    }

    private static void shouldNotFindByMobileAndNotExistingEmail() {
        // when
        Person receivedPerson = SERVICE.find(null, null, null, "501554332", "jan.kowalski@wrongmail.com", null, null);
        // then
        assert receivedPerson == null : testFailed(null, receivedPerson);
        testPassed();
    }

    private static void shouldFindByType() {
        // when
        List<Person> receivedPeople = SERVICE.getByType(Type.internal);
        // then
        assert receivedPeople != null : testFailed(TEST_PERSON, null);
        assert !receivedPeople.isEmpty() : testFailed(TEST_PERSON, null);
        //when
        Person receivedPerson = SERVICE.getByType(Type.internal).get(0);
        // then
        assert Objects.equals(TEST_PERSON, receivedPerson) : testFailed(TEST_PERSON, receivedPerson);
        testPassed();
    }

    private static void shouldNotFindByType() {
        // when
        List<Person> receivedPeople = SERVICE.getByType(Type.external);
        // then
        assert receivedPeople == null || receivedPeople.isEmpty() : testFailed(null, receivedPeople);
        testPassed();
    }

    private static void shouldFindByFirstName() {
        // when
        List<Person> receivedPeople = SERVICE.getByFirstName(TEST_PERSON.getFirstName());
        // then
        assert receivedPeople != null : testFailed(TEST_PERSON, receivedPeople);
        assert !receivedPeople.isEmpty() : testFailed(TEST_PERSON, receivedPeople);
        // when
        Person receivedPerson = receivedPeople.get(0);
        //then
        assert Objects.equals(TEST_PERSON, receivedPerson) : testFailed(TEST_PERSON, receivedPerson);
        testPassed();
    }

    private static void shouldFindByLastName() {
        // when
        List<Person> receivedPeople = SERVICE.getByLastName(TEST_PERSON.getLastName());
        // then
        assert receivedPeople != null : testFailed(TEST_PERSON, receivedPeople);
        assert !receivedPeople.isEmpty() : testFailed(TEST_PERSON, receivedPeople);
        // when
        Person receivedPerson = receivedPeople.get(0);
        //then
        assert Objects.equals(TEST_PERSON, receivedPerson) : testFailed(TEST_PERSON, receivedPerson);
        testPassed();
    }

    private static void shouldNotFindByNotExistingLastName() {
        // when
        List<Person> receivedPeople = SERVICE.getByLastName("asd");
        // then
        assert receivedPeople == null || receivedPeople.isEmpty() : testFailed(null, receivedPeople);
        testPassed();
    }

    private static void shouldFindByEmail() {
        // when
        Person receivedPerson = SERVICE.getByEmail(TEST_PERSON.getEmail());
        // then
        assert Objects.equals(TEST_PERSON, receivedPerson) : testFailed(TEST_PERSON, receivedPerson);
        testPassed();
    }

    private static void shouldFindByMobile() {
        // when
        Person receivedPerson = SERVICE.getByMobile(TEST_PERSON.getMobile());
        // then
        assert Objects.equals(TEST_PERSON, receivedPerson) : testFailed(TEST_PERSON, receivedPerson);
        testPassed();
    }

    private static void shouldFindByPesel() {
        // when
        Person receivedPerson = SERVICE.getByPesel(TEST_PERSON.getPesel());
        // then
        assert Objects.equals(TEST_PERSON, receivedPerson) : testFailed(TEST_PERSON, receivedPerson);
        testPassed();
    }

    private static void shouldNotFindByNotExistingPesel() {
        // when
        Person receivedPerson = SERVICE.getByPesel("12345678910");
        // then
        assert receivedPerson == null : testFailed(TEST_PERSON, receivedPerson);
        testPassed();
    }

    private static void shouldCreatePerson() {
        // given
        Person testPerson = new Person("123", "Anna", "Dąbrowska", "501554332", "jan.kowalski@mail.com", "8910145862", Type.external);
        // when
        SERVICE.create(testPerson);
        // then
        Person receivedPerson = SERVICE.getByPersonId("123");
        assert Objects.equals(testPerson, receivedPerson) : testFailed(testPerson, receivedPerson);
        testPassed();
        // clean
        SERVICE.remove("123");
    }

    private static void shouldModifyPerson() {
        // given
        Person currentPerson = SERVICE.getByPersonId(TEST_PERSON.getPersonId());
        currentPerson.setEmail("jan.kowalski@newmail.com");
        // when
        SERVICE.modify(currentPerson);
        Person receivedPerson = SERVICE.getByPersonId(currentPerson.getPersonId());
        // then
        assert Objects.equals(currentPerson, receivedPerson) : testFailed(currentPerson, receivedPerson);
        testPassed();
    }

    private static void shouldRemovePerson() {
        // when
        boolean isRemoved = SERVICE.remove(TEST_PERSON.getPersonId());
        // then
        assert isRemoved : testFailed(Boolean.TRUE, Boolean.FALSE);
        testPassed();
    }

    private static void testPassed() {
        System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName() + " was successful");
    }

    private static String testFailed(Object expected, Object received) {
        return String.format("%s failed!\nExpected:%s\nReceived:%s", Thread.currentThread().getStackTrace()[2].getMethodName(), expected, received);
    }

}
