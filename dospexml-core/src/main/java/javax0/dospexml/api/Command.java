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

    default CommandResult<RET> execute(CommandContext ctx) {
        final var nodes = ctx.nodeList();

        final var results = new ArrayList<CommandResult<?>>();
        try (final var __ = ctx.processor().open()) {
            for (final var node : nodes) {
                if (node.getNodeType() == Node.TEXT_NODE ||
                    node.getNodeType() == Node.CDATA_SECTION_NODE) {
                    if (this instanceof AllNodesProcessing) {
                        results.add(new TextNode(node.getTextContent()));
                    }
                } else if (node.getNodeType() != Node.COMMENT_NODE
                    && node.getNodeType() != Node.PROCESSING_INSTRUCTION_NODE) {
                    results.add(ctx.process(node));
                }
            }
        }
        return evaluate(ctx, results);
    }

    default String name() {
        return this.getClass().getSimpleName();
    }
}
