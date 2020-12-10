package javax0.dospexml.basic;

import javax0.dospexml.support.TestScript;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class TestBasicCommands {

    @Test
    @DisplayName("Every xml has to have its own")
    void testAllXmlFiles() throws ParserConfigurationException, SAXException, IOException {
        TestScript.with("").execute(this);
    }
}
