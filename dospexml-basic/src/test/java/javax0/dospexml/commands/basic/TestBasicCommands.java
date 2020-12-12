package javax0.dospexml.commands.basic;

import javax0.dospexml.testsupport.Test;
import javax0.dospexml.testsupport.TestCommands;

import java.io.File;

public class TestBasicCommands {

    @TestCommands
    void testAllXmlFiles(File input, String description) throws Exception {
        Test.with().execute(input);
    }
}
