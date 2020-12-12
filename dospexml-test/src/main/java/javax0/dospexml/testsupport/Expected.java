package javax0.dospexml.testsupport;

import javax0.dospexml.api.AllNodesProcessing;
import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import org.w3c.dom.Node;

class Expected implements Command<Void>, AllNodesProcessing {
    @Override
    public CommandResult<Void> execute(CommandContext ctx) {
        final var nodes = ctx.nodeList();
        if (nodes.size() != 1 || (nodes.get(0).getNodeType() != Node.TEXT_NODE && nodes.get(0).getNodeType() != Node.CDATA_SECTION_NODE)) {
            throw ctx.exception("Test command 'Expected' needs one text node in it nothing else.");
        }
        ((TestContext.Holder) ctx.globalContext().get()).expected.append(nodes.get(0).getTextContent());
        return null;
    }
}
