package sadowski.wojciech.app.person;

import org.xml.sax.Attributes;
import sadowski.wojciech.app.parser.handler.XMLHandler;

public class PersonHandler extends XMLHandler {
    private final String PERSON_ID = "personId";
    private final String FIRST_NAME = "firstName";
    private final String LAST_NAME = "lastName";
    private final String MOBILE = "mobile";
    private final String EMAIL = "email";
    private final String PESEL = "pesel";

    private Person person;
    private StringBuilder elementValue;

    public PersonHandler() {
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void startDocument() {
        this.person = new Person();
        super.setObject(person);
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) {
        switch (qName) {
            case PERSON_ID, PESEL, FIRST_NAME, LAST_NAME, MOBILE, EMAIL:
                elementValue = new StringBuilder();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case PERSON_ID:
                person.setPersonId(elementValue.toString());
                break;
            case FIRST_NAME:
                person.setFirstName(elementValue.toString());
                break;
            case LAST_NAME:
                person.setLastName(elementValue.toString());
                break;
            case MOBILE:
                person.setMobile(elementValue.toString());
                break;
            case EMAIL:
                person.setEmail(elementValue.toString());
                break;
            case PESEL:
                person.setPesel(elementValue.toString());
                break;
        }
    }

}
