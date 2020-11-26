package javax0.dysnow;

import javax0.dysnow.api.CommandContext;
import javax0.dysnow.api.CommandResult;
import javax0.dysnow.api.ExecutionException;
import javax0.dysnow.api.Input;
import javax0.dysnow.api.NamedCommand;
import javax0.dysnow.commands.AbstractVoidTextCommand;
import javax0.dysnow.engine.CommandRegister;
import javax0.dysnow.engine.Processor;
import javax0.dysnow.xmlinput.XmlInput;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class TestSampeProgram {

    private static class Program<T> implements NamedCommand<T> {

        @Override
        public CommandResult<T> evaluate(CommandContext ctx, List<CommandResult<?>> results) throws ExecutionException {
            return (CommandResult<T>) results.get(results.size() - 1);
        }

        @Override
        public String name() {
            return "program";
        }
    }

    private static class Puts extends AbstractVoidTextCommand implements NamedCommand<Void> {
        @Override
        public void evaluate(String s) {
            System.out.println(s);
        }

        @Override
        public String name() {
            return "puts";
        }
    }


    @Test
    void testSampleProgram() throws IOException, SAXException, ParserConfigurationException, ExecutionException {
        Input in = new XmlInput(this.getClass().getClassLoader().getResourceAsStream("input.xml"));
        final var doc = in.getDocument();
        final var processor = new Processor(new CommandRegister());
        try (final var r = processor.commandRegister().open()) {
            r.register("test:dysnow", new Program<>(), new Puts());
            processor.process(doc);
        }
    }

}
