package javax0.dospexml.api;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * A command can execute some code.
 */
public interface Command<RET> {

    default CommandResult<RET> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        return null;
    }

    class TextNode implements CommandResult<String> {
        public final String content;

        public TextNode(String content) {
            this.content = content;
        }

        @Override
        public String get() {
            return content;
        }

        @Override
        public Class<?> type() {
            return String.class;
        }

        @Override
        public boolean isVoid() {
            return false;
        }
    }

    class DefaultArgumentManager implements ArgumentManager {
        private static final ArgumentManager instance = new DefaultArgumentManager();
    }

    class AllNodesProcessingArgumentManager implements ArgumentManager {
        private static final ArgumentManager instance = new AllNodesProcessingArgumentManager();

        @Override
        public boolean needsTextSegments() {
            return true;
        }
    }

    default ArgumentManager argumentManager() {
        if (this instanceof AllNodesProcessing) {
            return AllNodesProcessingArgumentManager.instance;
        } else {
            return DefaultArgumentManager.instance;
        }
    }

    default CommandResult<RET> execute(CommandContext ctx) {
        final var nodes = ctx.nodeList();
        final var manager = argumentManager();
        if (manager.min() >= 0 && manager.min() > nodes.size()) {
            throw ctx.exception("Command {" + ctx.node().getNamespaceURI() + "}" + ctx.node().getLocalName() + " needs minimum " + manager.min() + " arguments and has " + nodes.size());
        }
        if (manager.max() >= 0 && manager.max() < nodes.size()) {
            throw ctx.exception("Command {" + ctx.node().getNamespaceURI() + "}" + ctx.node().getLocalName() + " needs maximum " + manager.max() + " arguments and has " + nodes.size());
        }

        final var results = new ArrayList<CommandResult<?>>();
        try (final var __ = ctx.processor().open()) {
            for (final var node : nodes) {
                if (node.getNodeType() == Node.TEXT_NODE ||
                    node.getNodeType() == Node.CDATA_SECTION_NODE) {
                    if (manager.needsTextSegments()) {
                        results.add(new TextNode(node.getTextContent()));
                    }
                } else if (node.getNodeType() != Node.COMMENT_NODE
                    && node.getNodeType() != Node.PROCESSING_INSTRUCTION_NODE) {
                    results.add(ctx.process(node));
                }
            }
        }
        argumentManager().validateArguments(results);
        return evaluate(ctx, results);
    }

    interface ArgumentManager {
        default int min() {
            return -1;
        }

        default int max() {
            return -1;
        }

        default boolean needsTextSegments() {
            return false;
        }

        default void validateArguments(List<CommandResult<?>> results) {
        }
    }

}
