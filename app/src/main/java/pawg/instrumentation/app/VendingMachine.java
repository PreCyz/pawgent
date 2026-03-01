package pawg.instrumentation.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class VendingMachine {
    private static final Logger LOGGER = LoggerFactory.getLogger(VendingMachine.class);

    public static void buy(int itemId) {
        try {
            TimeUnit.MILLISECONDS.sleep((long)(Math.random() * 2000));
            LOGGER.info("[Application] Successfully purchased item : {} ", itemId);
        } catch (InterruptedException e) {
            e.printStackTrace(System.out);
        }
    }

    public static void sell(int itemId) {
        try {
            TimeUnit.MILLISECONDS.sleep((long)(Math.random() * 2000));
            LOGGER.info("[Application] Successfully sold item : {} ", itemId);
        } catch (InterruptedException e) {
            e.printStackTrace(System.out);
        }
    }
}
