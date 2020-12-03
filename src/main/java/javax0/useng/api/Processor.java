package javax0.useng.api;

import javax0.useng.engine.CommandRegister;
import org.w3c.dom.Document;

public interface Processor extends AutoCloseable {
    CommandResult<?> process(Document doc);

    CommandRegister commandRegister();

    Processor open();

    void close();

}