package javax0.dospexml.testsupport;

import javax0.dospexml.api.CommandContext;

class TestContext implements CommandContext.GlobalContext<TestContext.Holder> {
    static class Holder {
        final StringBuilder expected = new StringBuilder();
    }

    final Holder holder = new Holder();

    @Override
    public Holder get() {
        return holder;
    }
}
