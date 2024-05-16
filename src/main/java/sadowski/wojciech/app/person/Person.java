package sadowski.wojciech.app.person;

import sadowski.wojciech.app.annotation.XMLElement;
import sadowski.wojciech.app.person.type.Type;

import java.util.Objects;

public class Person {
    @XMLElement
    private String personId;
    @XMLElement
    private String firstName;
    @XMLElement
    private String lastName;
    @XMLElement
    private String mobile;
    @XMLElement
    private String email;
    @XMLElement
    private String pesel;
    private Type type;

    public Person() {
    }

    public Person(String personId, String firstName, String lastName, String mobile, String email, String pesel, Type type) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.pesel = pesel;
        this.type = type;
    }

    public String getPersonId() {
        return personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getPesel() {
        return pesel;
    }

    public Type getType() {
        return type;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, firstName, lastName, mobile, email, pesel, type);
    }

    @Override
    public boolean equals(Object otherObject) {
        if(this == otherObject) return true;
        if(otherObject == null) return false;
        if(getClass() != otherObject.getClass()) return false;
        Person other = (Person) otherObject;
        return Objects.equals(personId, other.personId)
                && Objects.equals(firstName, other.firstName)
                && Objects.equals(lastName, other.lastName)
                && Objects.equals(mobile, other.mobile)
                && Objects.equals(email, other.email)
                && Objects.equals(pesel, other.pesel)
                && Objects.equals(type, other.type);
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId='" + personId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", pesel='" + pesel + '\'' +
                ", type=" + type +
                '}';
    }

}
