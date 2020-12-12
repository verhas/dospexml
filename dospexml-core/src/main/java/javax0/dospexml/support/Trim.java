package javax0.dospexml.support;

import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandResult;

import java.util.ArrayList;
import java.util.List;

public class Trim {

    /**
     * Cut off the text nodes if there are any non-text nodes, or just return the original list if there is none.
     *
     * @param original the original list of nodes
     * @return the trimmed list
     */
    public static List<CommandResult<?>> trim(List<CommandResult<?>> original) {
        final List<CommandResult<?>> trimmed = new ArrayList<>();
        for (final var result : original) {
            if (!(result instanceof Command.TextNode)) {
                trimmed.add(result);
            }
        }
        if (trimmed.size() > 0) {
            return trimmed;
        } else {
            return original;
        }
    }

}
