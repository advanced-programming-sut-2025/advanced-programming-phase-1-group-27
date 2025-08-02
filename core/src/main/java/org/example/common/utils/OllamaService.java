package org.example.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class OllamaService {
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    private static String getResponse(Map<String, Object> requestMap) throws IOException, InterruptedException {
        String requestBody = mapper.writeValueAsString(requestMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        Map<String, Object> responseMap = mapper.readValue(response.body(), Map.class);
        return (String) responseMap.get("response");
    }

    public static String queryModel(String model, String prompt) throws IOException, InterruptedException {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("model", model);
        requestMap.put("prompt", prompt);
        requestMap.put("stream", false);
        return getResponse(requestMap);
    }

    public static String queryModelWithRole(String model, String role, String prompt) throws IOException, InterruptedException {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("model", model);
        requestMap.put("system", role);
        requestMap.put("prompt", prompt);
        requestMap.put("stream", false);
        return getResponse(requestMap);
    }
}
