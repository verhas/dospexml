package javax0.dospexml.support;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class CommandTestFilesProvider implements ArgumentsProvider, AnnotationConsumer<TestCommands> {

    private Pattern only = null;

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        Class<?> klass = extensionContext.getRequiredTestClass();
        final var files = Objects.requireNonNull(new File(klass.getResource(".").getPath())
            .listFiles((dir, name) -> name.endsWith(".xml") && only.matcher(name).find()));
        final Arguments[] args = getArguments(files);
        return Arrays.stream(args);
    }

    private static Arguments[] getArguments(File[] files) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);

        final Arguments[] args = new Arguments[files.length];
        for (int i = 0; i < files.length; i++) {
            final var file = files[i];
            DocumentBuilder db = factory.newDocumentBuilder();
            final var doc = db.parse(new FileInputStream(file));
            args[i] = Arguments.of(file,
                getDisplayName(doc.getDocumentElement().getChildNodes()).orElse(file.getName()));
        }
        return args;
    }

    private static Optional<String> getDisplayName(NodeList topChildren) {
        for (int j = 0; j < topChildren.getLength(); j++) {
            final var kid = topChildren.item(j);
            final var tag = kid.getNodeName().replaceAll("^.*:", "");
            if (tag.equals("DisplayName")) {
                return Optional.ofNullable(kid.getTextContent());
            }
        }
        return Optional.empty();
    }

    @Override
    public void accept(TestCommands testCommands) {
        this.only = Pattern.compile(testCommands.only());
    }
}
