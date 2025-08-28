package com.telegram.vitbot.chatgpt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ChatGPTClient {

    private final String API_KEY;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ChatGPTClient(String apiKey) {
        this.API_KEY = apiKey;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public String ask(String prompt) throws Exception {
        // Формируем JSON-тело запроса
        String jsonBody = """
                {
                    "model": "gpt-3.5-turbo",
                    "messages": [
                        {"role": "user", "content": "%s"}
                    ],
                    "max_tokens": 150,
                    "temperature": 0.7
                }
                """.formatted(prompt);

        // Создаём HTTP-запрос
        String API_URL = "https://api.openai.com/v1/chat/completions";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .timeout(Duration.ofSeconds(30))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Ошибка API: " + response.statusCode() + " - " + response.body());
            }

            return parseResponse(response.body());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при отправке запроса: " + e.getMessage(), e);
        }
    }

    private String parseResponse(String responseBody) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(responseBody);
        try {
            return root.path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText()
                    .trim();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось распарсить ответ: " + responseBody);
        }
    }
}