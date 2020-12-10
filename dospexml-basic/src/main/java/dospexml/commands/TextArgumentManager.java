package dospexml.commands;

import dospexml.api.Command;

public class TextArgumentManager implements Command.ArgumentManager {
    public static final Command.ArgumentManager INSTANCE = new TextArgumentManager();

    @Override
    public int min() {
        return 1;
    }

    @Override
    public int max() {
        return -1;
    }

    @Override
    public boolean needsTextSegments() {
        return true;
    }
}
