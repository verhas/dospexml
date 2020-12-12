package javax0.dospexml.api;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ExecutionException extends RuntimeException {

    final public CommandContext ctx;

    public ExecutionException() {
        ctx = null;
    }

    public ExecutionException(Exception s) {
        super(s);
        ctx = null;
    }

    public ExecutionException(String s) {
        this(s, null);
    }


    public ExecutionException(String s, CommandContext ctx) {
        super(s);
        this.ctx = ctx;
        enrich();
    }

    private void enrich() {
        final var trace = getStackTrace();
        final String methodName = calculateMethodName();
        final String fileName = getFileName();
        final int lineNumber = getLineNumber();
        trace[0] = new StackTraceElement("", methodName, fileName, lineNumber);
        setStackTrace(trace);
    }

    private String calculateMethodName() {
        final String methodName;
        final var sb = new StringBuilder();
        Node lastNode = null;
        for (var node = ctx.node(); node != null; node = node.getParentNode()) {
            var index = countNodePosition(node, lastNode);
            if (node.getLocalName() != null) {
                sb.insert(0, node.getLocalName() + index);
            }
            lastNode = node;
        }
        return "//" + sb.toString();
    }

    private String getFileName() {
        return ctx.parameter("_originalFileName").orElse("__FILE__");
    }

    private int getLineNumber() {
        return ctx.intParameter("_originalLineNumber").orElse(-1);
    }

    private static String countNodePosition(Node node, Node lastNode) {
        int index;
        if (lastNode != null) {
            NodeList children = node.getChildNodes();
            int n = children.getLength();
            int count = 0;
            for (int i = 0; i < n; i++) {
                final var child = children.item(i);
                if (child == lastNode) {
                    index = count;
                    if (index == 0) {
                        return "/";
                    } else {
                        return "[" + index + "]/";
                    }
                }
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    count++;
                }
            }
        }
        return "";
    }
}
