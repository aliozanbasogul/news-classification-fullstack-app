package com.example.newsclassification.Controllers;

import com.example.newsclassification.Entities.News;
import com.example.newsclassification.Services.NewsService;
import com.example.newsclassification.Services.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class NewsController {

    private final NewsService newsService;
    private final OpenAIService openAIService;

    @Autowired
    public NewsController(NewsService newsService, OpenAIService openAIService) {
        this.newsService = newsService;
        this.openAIService = openAIService;
    }

    @PostMapping("/classify-news")
    public Map<String, List<News>> classifyNews(@RequestBody Map<String, String> request) {
        String city = request.get("city");

        List<News> allNews = newsService.getNews();

        return openAIService.classifyNewsByCity(allNews, city);
    }
}
