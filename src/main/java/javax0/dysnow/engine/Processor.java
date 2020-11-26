package javax0.dysnow.engine;

import javax0.dysnow.api.CommandResult;
import javax0.dysnow.api.ExecutionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Processor implements javax0.dysnow.api.Processor {

    final CommandRegister register;

    public Processor(CommandRegister register) {
        this.register = register;
    }

    @Override
    public CommandResult<?> process(Node node) throws ExecutionException {
        final var ctx = new SimpleCommandContext(this, node);
        final var command = register.get(node.getNamespaceURI(), node.getLocalName());
        return command.orElseThrow(() -> new ExecutionException("Command {" + node.getNamespaceURI() + "}" + node.getLocalName() + " is not found")).execute(ctx);
    }

    @Override
    public CommandResult<?> process(Document doc) throws ExecutionException {
        Element e = doc.getDocumentElement();
        return process(e);
    }

    @Override
    public CommandRegister commandRegister() {
        return register;
    }
}
