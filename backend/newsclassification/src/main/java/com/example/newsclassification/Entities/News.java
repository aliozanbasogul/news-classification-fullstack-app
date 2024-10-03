package com.example.newsclassification.Entities;

import jakarta.persistence.*;

@Entity
public class News {

    @Id
    @SequenceGenerator(
            name = "news_sequence",
            sequenceName = "news_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "news_sequence"
    )
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String keywords;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String text;

    public News() {}

    public News(String keywords, String title, String summary, String text) {
        this.keywords = keywords;
        this.title = title;
        this.summary = summary;
        this.text = text;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", keywords='" + keywords + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
