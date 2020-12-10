package javax0.dospexml.support;

import dospexml.api.NamedCommand;
import dospexml.engine.Processor;
import dospexml.engine.Register;
import dospexml.input.Input;
import dospexml.input.XmlInput;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class TestScript {
    final String uri;
    final NamedCommand<?>[] commands;

    private TestScript(String uri, NamedCommand<?>[] commands) {
        this.uri = uri;
        this.commands = commands;
    }

    public static TestScript with() {
        return new TestScript("", new NamedCommand[0]);
    }

    public static TestScript with(String uri, NamedCommand<?>... commands) {
        return new TestScript(uri, commands);
    }

    public void execute(Object test) throws ParserConfigurationException, SAXException, IOException {
        for (final var file : Objects.requireNonNull(new File(test.getClass().getResource(".").getPath())
            .listFiles((dir, name) -> name.endsWith(".xml")))) {
            final String[] result;
            try {
                result = execute(file);
            } catch (Exception e) {
                enrichException(file, e);
                throw e;
            }
            final var expected = result[0];
            final var actual = result[1];
            if (expected == null && actual == null) {
                return;
            }
            if (expected == null || !expected.equals(actual)) {
                throw new AssertionError("expected: " + expected + " but was: " + actual);
            }
        }
    }

    private static void enrichException(File file, Exception e) {
        final var trace = e.getStackTrace();
        trace[0] = new StackTraceElement(file.getName(), trace[0].getMethodName(), trace[0].getFileName(), trace[0].getLineNumber());
        e.setStackTrace(trace);
    }


    public String[] execute(File file) throws IOException, SAXException, ParserConfigurationException {
        final ByteArrayOutputStream baos;
        new FileInputStream(file).transferTo(baos = new ByteArrayOutputStream());
        return execute(baos.toString(StandardCharsets.UTF_8));
    }

    public String[] execute(String input) throws IOException, SAXException, ParserConfigurationException {
        Input in = new XmlInput(input);
        final Document doc = in.getDocument();
        final var context = new TestContext();
        try (final var output = new ByteArrayOutputStream()) {
            try (final var print = new PrintStream(output)) {
                try (final var processor = new Processor(context)) {
                    Register.withProcessor(processor).registerBasicCommands("useng:basic", print);
                    processor.commandRegister().register("test", new Expected(), new Throws(), new DisplayName());
                    processor.process(doc);
                }
            }
            return new String[]{context.get().expected.toString(), output.toString()};
        }
    }

}
