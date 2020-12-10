package javax0.dospexml.support;

import dospexml.api.AllNodesProcessing;
import dospexml.api.CommandContext;
import dospexml.api.CommandResult;
import dospexml.api.NamedCommand;
import org.w3c.dom.Node;

class DisplayName implements NamedCommand<Void>, AllNodesProcessing {
    @Override
    public CommandResult<Void> execute(CommandContext ctx) {
        final var nodes = ctx.nodeList();
        if (nodes.size() != 1 || (nodes.get(0).getNodeType() != Node.TEXT_NODE && nodes.get(0).getNodeType() != Node.CDATA_SECTION_NODE)) {
            throw ctx.exception("Test command 'Expected' needs one text node in it nothing else.");
        }
        ((TestContext.Holder) ctx.globalContext().get()).displayName.append(nodes.get(0).getTextContent());
        return null;
    }
}
