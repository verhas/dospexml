package javax0.dysnow.api;

import org.w3c.dom.Document;

/**
 * An input can read the DOM structure from some input source. The trivial implementation is the XML reading.
 */
public interface Input {

    Document getDocument();

}
