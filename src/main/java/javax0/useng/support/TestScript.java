package javax0.useng.support;

import javax0.useng.api.CommandContext;
import javax0.useng.api.ExecutionException;
import javax0.useng.api.NamedCommand;
import javax0.useng.engine.Processor;
import javax0.useng.engine.Register;
import javax0.useng.input.Input;
import javax0.useng.input.XmlInput;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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

    private static class TestContext implements CommandContext.GlobalContext {
        private final StringBuilder sb = new StringBuilder();

        @Override
        public Object get() {
            return sb;
        }
    }

    public void execute(Object test, TriConsumer report) throws ParserConfigurationException, SAXException, IOException {
        for (final var file : Objects.requireNonNull(new File(test.getClass().getResource(".").getPath())
            .listFiles((dir, name) -> name.endsWith(".xml")))) {
            final String[] result;
            try {
                result = execute(file);
            } catch (Exception e) {
                enrichException(file, e);
                throw e;
            }
            report.apply(result[0], result[1], file.getName());
        }
    }

    private static void enrichException(File file, Exception e) {
        final var trace = e.getStackTrace();
        final String methodName = calculateMethodName(e);
        trace[0] = new StackTraceElement(file.getName(), methodName, file.getAbsolutePath(), 1);
        e.setStackTrace(trace);
    }

    private static String calculateMethodName(Exception e) {
        final String methodName;
        CommandContext ctx;
        if (e instanceof ExecutionException && (ctx = ((ExecutionException) e).ctx) != null) {
            final var sb = new StringBuilder();
            Node lastNode = null;
            for (var node = ctx.node(); node != null; node = node.getParentNode()) {
                var index = countNodePosition(node, lastNode);
                if (node.getLocalName() != null) {
                    sb.insert(0, node.getLocalName() + index);
                }
                lastNode = node;
            }
            methodName = "//" + sb.toString();
        } else {
            methodName = "";
        }
        return methodName;
    }

    private static String countNodePosition(Node node, Node lastNode) {
        int index = -1;
        if (lastNode != null) {
            NodeList children = node.getChildNodes();
            int n = children.getLength();
            int count = 0;
            for (int i = 0; i < n; i++) {
                final var child = children.item(i);
                if (child == lastNode) {
                    index = count;
                    if (index == 0) {
                        return "/";
                    } else {
                        return "[" + index + "]/";
                    }
                }
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    count++;
                }
            }
        }
        return "";
    }

    public String[] execute(File file) throws IOException, SAXException, ParserConfigurationException {
        final ByteArrayOutputStream baos;
        new FileInputStream(file).transferTo(baos = new ByteArrayOutputStream());
        return execute(baos.toString(StandardCharsets.UTF_8));
    }

    public String[] execute(String input) throws IOException, SAXException, ParserConfigurationException {
        Input in = new XmlInput(input);
        final Document doc = in.getDocument();
        final var expected = new TestContext();
        try (final var output = new ByteArrayOutputStream()) {
            try (final var print = new PrintStream(output)) {
                try (final var processor = new Processor(expected)) {
                    Register.withProcessor(processor).registerBasicCommands("useng:basic", print);
                    processor.commandRegister().register("test", new Expected(), new Throws());
                    processor.process(doc);
                }
            }
            return new String[]{expected.get().toString(), output.toString()};
        }
    }

}
