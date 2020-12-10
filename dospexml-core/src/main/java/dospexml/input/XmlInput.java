package dospexml.input;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class XmlInput implements Input {
    final Document doc;

    public XmlInput(InputStream is) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder db = factory.newDocumentBuilder();
        doc = db.parse(is);
    }


    public XmlInput(String string) throws ParserConfigurationException, IOException, SAXException {
        this(new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8)));
    }


    @Override
    public Document getDocument() {
        return doc;
    }
}
