package javax0.dysnow.api;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.net.URI;
import java.util.Optional;

public interface CommandContext {
    Processor processor();
    NodeList nodeList();
    Optional<String> nameSpace();
    String commandName();
    Optional<String> attribute(URI ns, String attr);
}
