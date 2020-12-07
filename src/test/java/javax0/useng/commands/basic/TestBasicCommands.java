package javax0.useng.commands.basic;

import javax0.useng.support.TestScript;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
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
