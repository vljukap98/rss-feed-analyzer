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
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "hotTopic", cascade = CascadeType.ALL)
    private Article article;
    @JsonIgnore
    @ManyToOne
    private Analysis analysis;

    public HotTopic() {
    }

    public HotTopic(Long id, String name, Integer occurrences, Article article, Analysis analysis) {
        this.id = id;
        this.name = name;
        this.occurrences = occurrences;
        this.article = article;
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

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }
}
