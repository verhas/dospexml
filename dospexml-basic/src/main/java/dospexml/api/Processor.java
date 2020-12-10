package dospexml.api;

import dospexml.engine.CommandRegister;
import org.w3c.dom.Document;

public interface Processor extends AutoCloseable {
    CommandResult<?> process(Document doc);

    CommandRegister commandRegister();

    Processor open();

    void close();

}