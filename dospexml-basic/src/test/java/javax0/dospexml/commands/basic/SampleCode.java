package javax0.dospexml.commands.basic;

import javax0.dospexml.engine.Processor;
import javax0.dospexml.input.XmlInput;
import org.junit.jupiter.api.Test;

public class SampleCode {
    @Test
    void sampleInvocation() throws Exception {
        //snippet sampleInvocation
        // load the script from the source
        final var iostream = this.getClass().getResourceAsStream("Add.xml");
        final var doc = new XmlInput(iostream).getDocument();

        // create a script processor
        final var p = new Processor(null);

        // register commands
        new CommandsBuilder().with(p).nameSpace("dospex:basic").register();

        // execute the script
        p.process(doc);
        // end snippet
    }
}
