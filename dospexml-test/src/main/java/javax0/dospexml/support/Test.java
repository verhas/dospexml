package javax0.dospexml.support;

import javax0.dospexml.api.CommandPackageBuilder;
import javax0.dospexml.api.NamedCommand;
import javax0.dospexml.engine.Processor;
import javax0.dospexml.input.Input;
import javax0.dospexml.input.XmlInput;
import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Test {
    final String uri;
    final NamedCommand<?>[] commands;

    private Test(String uri, NamedCommand<?>[] commands) {
        this.uri = uri;
        this.commands = commands;
    }

    public static Test with() {
        return new Test("", new NamedCommand[0]);
    }

    public static Test with(String uri, NamedCommand<?>... commands) {
        return new Test(uri, commands);
    }

    public void execute(Object test) throws Exception {
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


    public String[] execute(File file) throws Exception {
        final ByteArrayOutputStream baos;
        new FileInputStream(file).transferTo(baos = new ByteArrayOutputStream());
        return execute(baos.toString(StandardCharsets.UTF_8));
    }

    public String[] execute(String input) throws Exception {
        Input in = new XmlInput(input);
        final Document doc = in.getDocument();
        final var context = new TestContext();
        try (final var output = new ByteArrayOutputStream()) {
            try (final var print = new PrintStream(output)) {
                try (final var processor = new Processor(context)) {
                    ((CommandPackageBuilder) Class.forName("javax0.dospexml.commands.basic.CommandsBuilder").getConstructor().newInstance())
                        .with(processor).nameSpace("dospex:basic").using("puts", print).register();
                    processor.commandRegister().register("test", new Expected(), new Throws(), new DisplayName());
                    processor.process(doc);
                }
            }
            return new String[]{context.get().expected.toString(), output.toString()};
        }
    }

}
