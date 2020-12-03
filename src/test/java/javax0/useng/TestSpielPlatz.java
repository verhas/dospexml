package javax0.useng;

import javax0.useng.input.Input;
import javax0.useng.input.XmlInput;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class TestSpielPlatz {

    @Test
    void test() throws IOException, SAXException, ParserConfigurationException {
        Input in = new XmlInput(this.getClass().getClassLoader().getResourceAsStream("input.xml"));
        final var doc = in.getDocument();
        final var e = doc.getDocumentElement();
        System.out.println(e.getTagName());
        System.out.println();
        final var nodes = e.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            final var node = nodes.item(i);
            System.out.println("name " + node.getNodeName());
            System.out.println(node.getAttributes());
            System.out.println("nsuri " + node.getNamespaceURI());
            System.out.println("value '" + node.getNodeValue() + "'");
            System.out.println("baseuri " + node.getBaseURI());
            System.out.println("localname " + node.getLocalName());
            System.out.println("prefix " + node.getPrefix());
            System.out.println("type " + node.getNodeType());
            System.out.println("text '" + node.getTextContent() + "'");
            System.out.println("");
        }

    }

}
