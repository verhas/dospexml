package javax0.dysnow.api;

import javax0.dysnow.engine.CommandRegister;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.net.URISyntaxException;

public interface Processor {
    CommandResult<?> process(Node doc) throws ExecutionException;

    CommandResult<?> process(Document doc) throws ExecutionException;

    CommandRegister commandRegister();
}