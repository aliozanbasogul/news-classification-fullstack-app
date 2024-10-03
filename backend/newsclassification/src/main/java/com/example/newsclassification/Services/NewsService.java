package com.example.newsclassification.Services;

import com.example.newsclassification.Entities.News;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NewsService {

    public List<News> getNews() {
        List<News> newsList = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("news.csv"))))) {

            String[] data;
            csvReader.readNext();

            while ((data = csvReader.readNext()) != null) {
                if (data.length < 5) {
                    System.err.println("Skipping malformed line: " + String.join(",", data));
                    continue;
                }

                String keywords = data[1].replace("\"", "").trim();
                String summary = data[2].replace("\"", "").trim();
                String text = data[3].replace("\"", "").trim();
                String title = data[4].replace("\"", "").trim();

                News news = new News(keywords, title, summary, text);
                newsList.add(news);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newsList;
    }
}
