package pawg.instrumentation.agent.bb;

import net.bytebuddy.asm.Advice;
import pawg.instrumentation.agent.bb.service.Sender;

import java.util.logging.Logger;

public class TimerAdvice {

    @Advice.OnMethodEnter
    static long enter() {
        return System.nanoTime();
    }

    @Advice.OnMethodExit
    static void exit(@Advice.Enter long startTime) {
        Logger logger = Logger.getLogger(TimerAdvice.class.getName());
        long endTime = System.nanoTime();
        long durationMs = (endTime - startTime) / 1_000_000;

        logger.info("[AgentBB] Sold in: %d milliseconds.".formatted(durationMs));
//        System.out.printf("[AgentBB] Sold in: %d milliseconds.%n", duration);
        new Sender().send();
    }
}
