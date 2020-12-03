package javax0.useng.commands.intrinsic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.ExecutionException;
import javax0.useng.api.NamedCommand;
import javax0.useng.api.Version;

public class Require implements NamedCommand<Void> {
    @Override
    public CommandResult<Void> execute(CommandContext ctx) {
        final var requiredVersion = ctx.intParameter("version").orElseThrow(() -> new ExecutionException("Require command needs an int version attribute"));
        if (requiredVersion > Version.VERSION) {
            throw new ExecutionException("Current version is too low, it is " + Version.VERSION + " and the script needs " + requiredVersion);
        }
        if (requiredVersion < Version.ACCEPTS) {
            throw new ExecutionException("Accepted version is too high, it is " + Version.ACCEPTS + " and the script needs " + requiredVersion);
        }
        return null;
    }
}
