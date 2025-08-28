package com.telegram.vitbot.chatgpt;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.telegram.vitbot.secret.SecretKeys.CHAT_GPT_KEY;


public class OpenAIClient {

    private static final String OPENAI_API_KEY = CHAT_GPT_KEY;
    public static String sendMessageToChatGPT(String message) {
        try {
            URL url = new URL("https://api.openai.com/v1/chat/completions");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + OPENAI_API_KEY);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Формируем JSON запрос для ChatGPT
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "gpt-4"); // Или "gpt-3.5-turbo"

            JSONArray messages = new JSONArray();
            JSONObject systemMessage = new JSONObject();
            systemMessage.put("role", "system");
            systemMessage.put("content", "Ты — полезный личный ассистент.");
            messages.put(systemMessage);

            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", message);
            messages.put(userMessage);

            requestBody.put("messages", messages);

            // Отправка запроса
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Чтение ответа
            int status = connection.getResponseCode();
            InputStreamReader streamReader;

            if (status > 299) {
                streamReader = new InputStreamReader(connection.getErrorStream());
            } else {
                streamReader = new InputStreamReader(connection.getInputStream());
            }
            try (BufferedReader in = new BufferedReader(streamReader)) {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                JSONObject jsonResponse = new JSONObject(content.toString());
                if (jsonResponse.has("choices")) {
                    return jsonResponse.getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content").trim();
                } else {
                    return "Ошибка: нет ответа от ChatGPT.";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Произошла ошибка при подключении к OpenAI API.";
        }
    }
}
