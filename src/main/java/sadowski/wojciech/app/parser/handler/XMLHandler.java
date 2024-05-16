package sadowski.wojciech.app.parser.handler;

import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler {
    private Object object;

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

}
