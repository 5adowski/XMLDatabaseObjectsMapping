Application that provide reading and writing .xml files using Parser<T>

To parse object, develop class that extends XMLHandler and implements characters, startDocument, startElement and endElement methods (of super class DefaultHandler).
Add annotation @XMLElement to any field of your object that you want to be included in .xml file as an attribute.
Inject parser into your repository and provide XMLHandler class that you developed into parser constructor.

You can change the database directory by changing the DatabaseConfiguration.PEOPLE_PATH_CATALOG variable.

There is an example of implementation included, just check out the src/main/java/sadowski/wojciech/app/person catalog.
A few test of the PersonService operations are included: src/test/java/sadowski/wojciech/app/person/PersonServiceTest.java
