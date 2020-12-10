package dospexml.commands.intrinsic;

import dospexml.api.CommandContext;
import dospexml.api.CommandResult;
import dospexml.api.ExecutionException;
import dospexml.api.NamedCommand;
import dospexml.api.Version;

public class Require implements NamedCommand<Void> {
    @Override
    public CommandResult<Void> execute(CommandContext ctx) {
        final var requiredVersion = ctx.intParameter("version").orElseThrow(() -> new ExecutionException("Require command needs an int version attribute"));
        if (requiredVersion > Version.VERSION) {
            throw ctx.exception("Current version is too low, it is " + Version.VERSION + " and the script needs " + requiredVersion);
        }
        if (requiredVersion < Version.ACCEPTS) {
            throw ctx.exception("Accepted version is too high, it is " + Version.ACCEPTS + " and the script needs " + requiredVersion);
        }
        return null;
    }
}
