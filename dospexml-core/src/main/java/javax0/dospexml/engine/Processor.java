package javax0.dospexml.engine;

import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import javax0.dospexml.api.Query;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

public class Processor implements javax0.dospexml.api.Processor {

    int dynamicLevel = 0;
    final CommandRegister register;
    final CommandContext.GlobalContext<?> globalContext;
    final private Map<Object, StaticQueryMap<?, ?>> staticMap = new HashMap<>();
    final private Map<Object, DynamicQueryMap<?, ?>> dynamicMap = new HashMap<>();

    public Processor(CommandContext.GlobalContext<?> globalContext) {
        this.register = new CommandRegister();
        this.globalContext = globalContext;
        open();
    }

    CommandResult<?> process(Node node) {
        final var command = register.get(node.getNamespaceURI(), node.getLocalName())
            .orElseThrow(() -> new SimpleCommandContext(this, node, globalContext, false)
                .exception("Command {" + node.getNamespaceURI() + "}" + node.getLocalName() + " is not found"));
        final var ctx = new SimpleCommandContext(this, node, globalContext, command.argumentManager().needsTextSegments());
        return command.execute(ctx);
    }

    @Override
    public CommandResult<?> process(Document doc) {
        Element e = doc.getDocumentElement();
        return process(e);
    }

    @Override
    public CommandRegister commandRegister() {
        return register;
    }

    @Override
    public javax0.dospexml.api.Processor open() {
        dynamicLevel++;
        commandRegister().open();
        dynamicMap.values().forEach(DynamicQueryMap::open);
        return this;
    }

    @Override
    public void close() {
        dynamicLevel--;
        commandRegister().close();
        dynamicMap.values().forEach(DynamicQueryMap::close);
    }

    <K, V> Query<K, V> staticQuery(Object key, Node node) {
        StaticQueryMap<?, ?> map;
        if ((map = staticMap.get(key)) == null) {
            map = new StaticQueryMap<>();
            staticMap.put(key, map);
        }
        return (StaticQueryMap<K, V>.Query) map.new Query(node);
    }

    <K, V> Query<K, V> dynamicQuery(Object key) {
        DynamicQueryMap<?, ?> map;
        if ((map = dynamicMap.get(key)) == null) {
            map = new DynamicQueryMap<>(dynamicLevel);
            dynamicMap.put(key, map);
        }
        return (DynamicQueryMap<K, V>) map;
    }
}
