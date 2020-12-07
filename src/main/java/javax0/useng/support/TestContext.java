package javax0.useng.support;

import javax0.useng.api.CommandContext;

class TestContext implements CommandContext.GlobalContext<TestContext.Holder> {
    static class Holder {
        final StringBuilder expected = new StringBuilder();
        final StringBuilder displayName = new StringBuilder();
        final StringBuilder documentation = new StringBuilder();
    }
    final Holder holder = new Holder();
    @Override
    public Holder get() {
        return holder;
    }
}
