package javax0.useng.input;

import org.w3c.dom.Document;

/**
 * An input can read the DOM structure from some input source. The trivial implementation is the XML reading.
 */
public interface Input {

    /**
     * Different implementation of the input may read the script from different sources. The trivial implementation
     * reads the source from an XML file. Other sources may read files with different syntax, but they have to convert
     * it to DOM structure. This way the internal representation of the code is XML/DOM.
     *
     * @return the program structure as an XML document.
     */
    Document getDocument();

}
