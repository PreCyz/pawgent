package pawg.instrumentation.agent;

import com.sun.tools.attach.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class AgentLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(AgentLoader.class);

    static void main() throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        String agentFilePath = "agent\\target\\Agent-jar-with-dependencies.jar";
        String applicationName = "app\\target\\Application-jar-with-dependencies.jar";

        VirtualMachine vm = findVirtualMachine(applicationName);

        // load agent
        File agentFile = new File(agentFilePath);
        vm.loadAgent(agentFile.getAbsolutePath());
        LOGGER.info("Java agent loaded successfully");

        vm.detach();
        LOGGER.info("Process finished");
    }

    public static VirtualMachine findVirtualMachine(String name) throws IOException, AttachNotSupportedException {
        VirtualMachineDescriptor virtualMachineDescriptor = VirtualMachine.list()
                .stream()
                .filter(jvm -> jvm.displayName().contains(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find virtual machine with name: " + name));

        String pid = virtualMachineDescriptor.id();
        LOGGER.info("Attaching to JVM with PID: {}", pid);

        return VirtualMachine.attach(pid);
    }

}
