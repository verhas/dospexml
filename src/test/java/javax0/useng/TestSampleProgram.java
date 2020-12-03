package javax0.useng;

import javax0.useng.api.ExecutionException;
import javax0.useng.api.NamedCommand;
import javax0.useng.engine.Processor;
import javax0.useng.engine.Register;
import javax0.useng.input.Input;
import javax0.useng.input.XmlInput;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class TestSampleProgram {

    @Test
    void testSampleProgram() throws IOException, SAXException, ParserConfigurationException, ExecutionException {
        Input in = new XmlInput(this.getClass().getClassLoader().getResourceAsStream("input.xml"));
        final Document doc = in.getDocument();
        try (final var processor = new Processor(null)) {
            Register.withProcessor(processor).registerBasicCommands("useng:basic");
            processor.commandRegister().register("myApp:namespace", new MyCommand());
            processor.process(doc);
        }
    }

    private static class MyCommand implements NamedCommand<Integer> {

    }

}
