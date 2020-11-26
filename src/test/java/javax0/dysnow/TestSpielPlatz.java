package javax0.dysnow;

import javax0.dysnow.api.Input;
import javax0.dysnow.xmlinput.XmlInput;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TestSpielPlatz {

    @Test
    void test() throws IOException, SAXException, ParserConfigurationException {
        Input in = new XmlInput(this.getClass().getClassLoader().getResourceAsStream("input.xml"));
        final var doc = in.getDocument();
        final var e = doc.getDocumentElement();
        System.out.println(e.getTagName());
        System.out.println(e.getAttributes().getNamedItemNS("http://kaki.com","asa").getTextContent());
        System.out.println();
        final var nodes = e.getChildNodes();
        for( int i = 0 ; i <  nodes.getLength() ; i ++){
            final var node = nodes.item(i);
            System.out.println("name "+node.getNodeName());
            System.out.println("nsuri "+node.getNamespaceURI());
            System.out.println("value '"+node.getNodeValue()+"'");
            System.out.println("baseuri "+node.getBaseURI());
            System.out.println("localname "+node.getLocalName());
            System.out.println("prefix "+node.getPrefix());
            System.out.println("type "+node.getNodeType());
            System.out.println("text '"+node.getTextContent()+"'");
            System.out.println("");
        }

    }

}
