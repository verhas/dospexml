package javax0.dysnow.api;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * A command can execute some code.
 */
public interface Command<RET> {

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

        default void validateArguments(List<CommandResult<?>> results) throws ExecutionException {
        }
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
        private static final ArgumentManager instanceDefault = new DefaultArgumentManager();
    }

    default ArgumentManager argumentManager() {
        return DefaultArgumentManager.instanceDefault;
    }

    default CommandResult<RET> evaluate(CommandContext ctx, List<CommandResult<?>> results) throws ExecutionException {
        return null;
    }

    default CommandResult<RET> execute(CommandContext ctx) throws ExecutionException {
        NodeList nodes = ctx.nodeList();
        final var manager = argumentManager();
        if (manager.min() >= 0 && manager.min() > nodes.getLength()) {
            throw new ExecutionException();
        }
        if (manager.max() >= 0 && manager.max() < nodes.getLength()) {
            throw new ExecutionException();
        }

        final var results = new ArrayList<CommandResult<?>>();
        try (final var __ = ctx.processor().commandRegister().open()) {
            for (int i = 0; i < nodes.getLength(); i++) {
                final var node = nodes.item(i);
                if (node.getNodeType() == Node.TEXT_NODE) {
                    if (manager.needsTextSegments()) {
                        results.add(new TextNode(node.getTextContent()));
                    }
                } else {
                    results.add(ctx.processor().process(nodes.item(i)));
                }
            }
        }
        argumentManager().validateArguments(results);
        return evaluate(ctx, results);
    }

}
