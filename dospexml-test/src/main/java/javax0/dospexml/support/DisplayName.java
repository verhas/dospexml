package javax0.dospexml.support;

import javax0.dospexml.api.AllNodesProcessing;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import javax0.dospexml.api.NamedCommand;
import org.w3c.dom.Node;

class DisplayName implements NamedCommand<Void>, AllNodesProcessing {
    @Override
    public CommandResult<Void> execute(CommandContext ctx) {
        return null;
    }
}
