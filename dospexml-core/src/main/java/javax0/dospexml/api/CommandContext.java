package javax0.dospexml.api;

import org.w3c.dom.Node;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.function.Function;

public interface CommandContext {
    GlobalContext<?> NO_GLOBAL_CONTEXT = null;

    /**
     * Global context is the data that connects the commands to the embedding application. The embedding application can
     * create a global context object and pass it to the processor. This global context can contain an arbitrary
     * application specific object of the type {@code T}. This object can provide data storage and callbacks that helps
     * the integration with the embedding application.
     * <p>
     * For example the test running provides a global context that contains a {@code T} object that hold various
     * StringBuilders. Commands, like {@code Expected} use a StringBuilder to create the output string. Then the
     * embedding application at the end uses this to perform the comparison between the actual and the expected output.
     * <p>
     * The global context hold the embedder specific object and it is NOT the object itself. This is to leave room for
     * later implementations when there can be some other functionality added to global context.
     *
     * @param <T> the type of the object held by the global context
     * @return the global context
     */
    <T> GlobalContext<T> globalContext();

    /**
     * Create and thow an ExecutionException with the message.
     *
     * @param message the message of the exception.
     * @return the exception, so the caller can {@code throw} it. It never returns but this return type helps the caller
     * to write code so that the compiler does not complain about unreachable code or missing return statement when
     * something was thrown already.
     */
    ExecutionException exception(String message);

    /**
     * @return the processor processing the XML structure
     */
    Processor processor();

    /**
     * Returns the collected children nodes. This list contains all the nodes if the current command has an argument
     * manager saying it needs text segments. More specifically {@code command.argumentManager().needsTextSegments()}
     * returns true for the command that is assigned to the current node. The default implementation of the argument
     * manager returns true if the command implements the interface {@code AllNodesProcessing}.
     *
     * @return the list of the children node. Based on the type of the current command it may or may not contain the
     * text nodes, like text, CData, comment and so on.
     */
    List<Node> nodeList();

    /**
     * @return the current node.
     */
    Node node();

    /**
     * @return the name space of the current node. There may not be a name space. In that case the returned value is
     * epmty.
     */
    Optional<String> nameSpace();

    /**
     * @return the local name of the tag, which is the name of the command. The name space together with the local name
     * identifies the command to be invoked. There is always a local name, thus the return value is not optional and is
     * never {@code null}.
     */
    String commandName();

    /**
     * @param ns   the name space of the attribute. Note that attributes do not inherit the name space from the XML tag
     *             on which there are on. Attributes are usually in the global name space unless a name space is
     *             explicitly specified.
     * @param attr the name of the attribute.
     * @return the text value of the attribute or empty if the attribute does not exist on the current node.
     */
    Optional<String> attribute(String ns, String attr);

    /**
     * Get the text value of the child node named {@code ns:attr}. For example if the current node is {@code Current}:
     *
     * <pre>{@code
     *  <Current xmlns:s="sampleNameSpace">
     *      <s:SampleText>this is a sample text</s:SampleText>
     *  </Current>
     * }</pre>
     * <p>
     * then calling {@code textNode("sampleNameSpace","SampleText")} will return {@code this is a sample text}.
     * <p>
     * This method is rarely used directly. Commands tend to let textual parameters to be specified in attributes OR in
     * child tags and this is implemented in the method {@link #parameter(String)}.
     *
     * @param ns   the name space of the node
     * @param attr the local name of the node
     * @return the textual value of the node or Empty optional if there is no such node.
     */
    Optional<String> textNode(String ns, String attr);

    /**
     * Get a textual parameter of the given name. This method finds a textual parameter taking it from an attribute or
     * from a child tag giving freedom to the user of the XML structure.
     * <p>
     * First it is checked that if the parameter is specified in an attribute that has the name {@code attr} and the
     * same name space as the current node. This is usually not the case, but this is the most specific. If this
     * attribute is not found then it checks the attribute with the name {@code attr} without name space. As a last
     * result it also tries to get the text value calling {@link #textNode(String, String)} with the current node's name
     * space and the given {@code attr} name.
     *
     * @param attr the name of the parameter.
     * @return the textual parameter or Empty optional if the parameter cannot be found.
     */
    Optional<String> parameter(String attr);

    private <T> Optional<T> tParameter(String attr, Function<String, T> converter) {
        final var p = parameter(attr);
        if (p.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(converter.apply(p.get()));
    }

    /**
     * Return a long parameter. This method calls {@link #parameter(String)} and then converts the result to a long.
     *
     * @param attr the name of the parameter
     * @return the optional long
     * @throws NumberFormatException when the parameter exists but it cannot be parsed as long
     */
    default OptionalLong longParameter(String attr) throws NumberFormatException {
        final var p = tParameter(attr, Long::parseLong);
        if (p.isEmpty()) {
            return OptionalLong.empty();
        }
        return OptionalLong.of(p.get());
    }

    /**
     * Return an int parameter. This method calls {@link #parameter(String)} and then converts the result to an int.
     *
     * @param attr the name of the parameter
     * @return the optional int
     * @throws NumberFormatException when the parameter exists but it cannot be parsed as an int
     */
    default OptionalInt intParameter(String attr) throws NumberFormatException {
        final var p = tParameter(attr, Integer::parseInt);
        if (p.isEmpty()) {
            return OptionalInt.empty();
        }
        return OptionalInt.of(p.get());
    }

    /**
     * Return a double parameter. This method calls {@link #parameter(String)} and then converts the result to a
     * double.
     *
     * @param attr the name of the parameter
     * @return the optional double
     * @throws NumberFormatException when the parameter exists but it cannot be parsed as a double
     */
    default OptionalDouble doubleParameter(String attr) throws NumberFormatException {
        final var p = tParameter(attr, Double::parseDouble);
        if (p.isEmpty()) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(p.get());
    }

    /**
     * Return a boolean parameter. This method calls {@link #parameter(String)} and then converts the result to a
     * boolean.
     *
     * @param attr the name of the parameter
     * @return the optional double
     */
    default Optional<Boolean> booleanParameter(String attr) {
        return tParameter(attr, Boolean::parseBoolean);
    }

    /**
     * Invokes the processor to process the node.
     *
     * @param node the node to process
     * @param <T>  the type we expect as a result
     * @return the result of the command execution
     */
    <T> CommandResult<T> process(Node node);


    <K, V> Query<K, V> staticQuery(Object key);

    <K, V> Query<K, V> dynamicQuery(Object key);

    interface GlobalContext<T> {
        T get();
    }
}
