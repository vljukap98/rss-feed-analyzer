package com.ljakovic.rssfeedanalyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 512)
    private String title;
    @Column(length = 2048)
    private String link;
    @JsonIgnore
    @OneToOne
    private HotTopic hotTopic;

    public Article() {
    }

    public Article(Long id, String title, String link, HotTopic hotTopic) {
        this.id = id;
        this.title = title;
        this.link = link;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public HotTopic getHotTopic() {
        return hotTopic;
    }

    public void setHotTopic(HotTopic hotTopic) {
        this.hotTopic = hotTopic;
    }
}
