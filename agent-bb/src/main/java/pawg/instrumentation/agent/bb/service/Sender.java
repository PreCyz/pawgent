package pawg.instrumentation.agent.bb.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class Sender {
    public void send() {
        var body = """
                [{
                "name":"agent-bb",
                "startDate":"2026-03-01T17:21:44",
                "boardMembers":1
                }]
                """;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/companies"))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            Logger.getLogger(getClass().getName()).info("This is response <%s>".formatted(response.body()));
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace(System.err);
        }
    }
}
