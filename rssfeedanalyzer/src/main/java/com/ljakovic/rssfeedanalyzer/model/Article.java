package com.ljakovic.rssfeedanalyzer.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String link;
    private String rssUrl;
    @ManyToOne
    private HotTopic hotTopic;

    public Article() {
    }

    public Article(Long id, String title, String description, String link, String rssUrl, HotTopic hotTopic) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.rssUrl = rssUrl;
        this.hotTopic = hotTopic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRssUrl() {
        return rssUrl;
    }

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    public HotTopic getHotTopic() {
        return hotTopic;
    }

    public void setHotTopic(HotTopic hotTopic) {
        this.hotTopic = hotTopic;
    }
}
