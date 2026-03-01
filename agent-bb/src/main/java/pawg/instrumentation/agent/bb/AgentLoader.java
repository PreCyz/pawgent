package pawg.instrumentation.agent.bb;

import com.sun.tools.attach.*;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class AgentLoader {
    private static final Logger LOGGER = Logger.getLogger(AgentLoader.class.getName());

    static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        String agentFilePath = "agent-bb\\target\\Agent-BB-jar-with-dependencies.jar";
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
        LOGGER.info("Attaching to JVM with PID: %s".formatted(pid));

        return VirtualMachine.attach(pid);
    }

}
