package pawg.instrumentation.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.*;

public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    static void main() {
        LOGGER.info("[Application] Starting application");
        var random = new Random();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleWithFixedDelay(
                () -> {
                    int itemId = random.nextInt(10000);
                    VendingMachine.buy(itemId);
                    VendingMachine.sell(itemId);
                },
                0,
                1,
                TimeUnit.SECONDS);
    }
}
