package javax0.dospexml.support;

import dospexml.api.CommandContext;

class TestContext implements CommandContext.GlobalContext<TestContext.Holder> {
    static class Holder {
        final StringBuilder expected = new StringBuilder();
        final StringBuilder displayName = new StringBuilder();
    }
    final Holder holder = new Holder();
    @Override
    public Holder get() {
        return holder;
    }
}
