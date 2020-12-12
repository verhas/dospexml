package javax0.dospexml.testsupport;

import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandPackageBuilder;
import javax0.dospexml.engine.Processor;
import javax0.dospexml.input.Input;
import javax0.dospexml.input.XmlInput;
import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Test {
    final String uri;
    final Command<?>[] commands;

    private Test(String uri, Command<?>[] commands) {
        this.uri = uri;
        this.commands = commands;
    }

    public static Test with() {
        return new Test("", new Command[0]);
    }

    public static Test with(String uri, Command<?>... commands) {
        return new Test(uri, commands);
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
