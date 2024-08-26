package com.ljakovic.rssfeedanalyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "hot_topic")
public class HotTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer occurrences;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "hotTopic", cascade = CascadeType.ALL)
    private List<Article> articles;
    @JsonIgnore
    @ManyToOne
    private Analysis analysis;

    public HotTopic() {
    }

    public HotTopic(Long id, String name, Integer occurrences, List<Article> articles, Analysis analysis) {
        this.id = id;
        this.name = name;
        this.occurrences = occurrences;
        this.articles = articles;
        this.analysis = analysis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(Integer occurrences) {
        this.occurrences = occurrences;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }
}
