package javax0.useng.commands.basic;

import javax0.useng.support.TestScript;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class TestBasicCommands {

    @Test
    void testAllXmlFiles() throws ParserConfigurationException, SAXException, IOException {
        TestScript.with("").execute(this, Assertions::assertEquals);
    }
}
