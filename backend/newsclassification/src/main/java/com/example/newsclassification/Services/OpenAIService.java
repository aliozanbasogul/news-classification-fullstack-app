package com.example.newsclassification.Services;

import com.example.newsclassification.Entities.News;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    private final RestTemplate restTemplate;

    public OpenAIService() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, List<News>> classifyNewsByCity(List<News> allNews, String city) {
        Map<String, List<News>> classifiedNews = new HashMap<>();

        // Initialize the lists
        List<News> globalNews = new ArrayList<>();
        List<News> newsclassification = new ArrayList<>();

        for (News news : allNews) {
            boolean isLocal = isnewsclassification(news, city);
            if (isLocal) {
                newsclassification.add(news);
            } else {
                globalNews.add(news);
            }
        }

        classifiedNews.put("global", globalNews);
        classifiedNews.put("local", newsclassification);

        return classifiedNews;
    }

    private boolean isnewsclassification(News news, String city) {
        String prompt = "Classify the following news article as 'local' or 'global' to the city " + city + ":"
                + "\nTitle: " + news.getTitle()
                + "\nSummary: " + news.getSummary()
                + "\nText: " + news.getText()
                + "\nPlease respond with only 'local' or 'global'. Do not include any other text.";

        System.out.println("Prompt sent to OpenAI: \n" + prompt);

        String apiUrl = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openAiApiKey);
        headers.set("Content-Type", "application/json");

        Map<String, Object> request = new HashMap<>();
        request.put("model", "gpt-4o-mini");
        request.put("max_tokens", 10);

        List<Map<String, String>> messages = new ArrayList<>();

        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "You are a helpful assistant that classifies news articles as either 'local' or 'global' to a city.");
        messages.add(systemMessage);

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);
        messages.add(userMessage);

        request.put("messages", messages);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class);

            System.out.println("Full Response from OpenAI: \n" + response.getBody());

            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            if (choices != null && !choices.isEmpty()) {
                System.out.println("First choice: \n" + choices.get(0));

                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                String gptResponse = (String) message.get("content");
                System.out.println("Response from OpenAI: \n" + gptResponse);

                if (gptResponse.trim().equalsIgnoreCase("local")) {
                    return true;
                } else if (gptResponse.trim().equalsIgnoreCase("global")) {
                    return false;
                }
            }
        } catch (HttpClientErrorException e) {
            System.err.println("HTTP Error during API call: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
