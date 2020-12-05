package javax0.useng.api;

public class ExecutionException extends RuntimeException {

    final public CommandContext ctx;

    public ExecutionException() {
        ctx = null;
    }

    public ExecutionException(Exception s) {
        super(s);
        ctx = null;
    }

    public ExecutionException(String s, CommandContext ctx) {
        super(s);
        this.ctx = ctx;
    }

    public ExecutionException(String s) {
        this(s, null);
    }
}
