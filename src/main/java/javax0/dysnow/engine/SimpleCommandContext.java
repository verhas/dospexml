package javax0.dysnow.engine;

import javax0.dysnow.api.CommandContext;
import javax0.dysnow.api.Processor;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URI;
import java.util.Optional;

public class SimpleCommandContext implements CommandContext {
    final javax0.dysnow.engine.Processor processor;
    private final NodeList nodeList;
    private final String nsuri;
    private final String localName;
    private final NamedNodeMap attributes;

    public SimpleCommandContext(javax0.dysnow.engine.Processor processor, Node node) {
        this.processor = processor;
        this.nodeList = node.getChildNodes();
        this.nsuri = node.getNamespaceURI();
        this.localName = node.getLocalName();
        attributes = node.getAttributes();
    }


    @Override
    public Processor processor() {
        return processor;
    }

    @Override
    public NodeList nodeList() {
        return nodeList;
    }

    @Override
    public Optional<String> nameSpace() {
        return Optional.ofNullable(nsuri);
    }

    @Override
    public String commandName() {
        return localName;
    }

    @Override
    public Optional<String> attribute(URI ns, String attr) {
        final var attrNode = attributes.getNamedItemNS(ns.toString(), attr);
        return Optional.ofNullable(attrNode).map(n -> n.getTextContent());
    }
}
