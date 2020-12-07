package javax0.useng.support;

import javax0.useng.api.AllNodesProcessing;
import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.ExecutionException;
import javax0.useng.api.NamedCommand;
import org.w3c.dom.Node;

class Documentation implements NamedCommand<Void>, AllNodesProcessing {
    @Override
    public CommandResult<Void> execute(CommandContext ctx) {
        final var nodes = ctx.nodeList();
        if (nodes.size() != 1 || (nodes.get(0).getNodeType() != Node.TEXT_NODE && nodes.get(0).getNodeType() != Node.CDATA_SECTION_NODE)) {
            throw ctx.exception("Test command 'Expected' needs one text node in it nothing else.");
        }
        ((TestContext.Holder) ctx.globalContext().get()).documentation.append(nodes.get(0).getTextContent());
        return null;
    }
}
