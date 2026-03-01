package pawg.instrumentation.agent.bb;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;
import java.util.logging.Logger;

public class PawgentBb {

    private static final Logger LOGGER = Logger.getLogger("PawgentBb");
    private static final String className = "pawg.instrumentation.app.VendingMachine";

    public static void premain(String agentArgs, Instrumentation inst) {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tF %1$tT [%4$-5s] [%3$s] %2$-30.30s: %5$s%n");
        LOGGER.info("[Agent-BB] Premain method");
        transformClass(inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tF %1$tT [%4$-5s] [%3$s] %2$-30.30s: %5$s%n");
        LOGGER.info("[Agent-BB] Agentmain method");
        transformClass(inst);
    }

    private static void transformClass(Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .type(ElementMatchers.named(className))
                .transform((builder, _, _, _, _) ->
                        builder.visit(Advice.to(TimerAdvice.class).on(ElementMatchers.named("sell")))
                )
                .installOn(instrumentation);
    }

}
