package javax0.dospexml.testsupport;

import javax0.dospexml.api.AllNodesProcessing;
import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;

class DisplayName implements Command<Void>, AllNodesProcessing {
    @Override
    public CommandResult<Void> execute(CommandContext ctx) {
        return null;
    }
}
