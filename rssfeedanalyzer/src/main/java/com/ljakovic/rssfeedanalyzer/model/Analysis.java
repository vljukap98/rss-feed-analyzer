package com.ljakovic.rssfeedanalyzer.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "analysis")
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateCreated;
    private String rssUrls;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "analysis", cascade = CascadeType.ALL)
    private List<HotTopic> hotTopics;

    public Analysis() {
    }

    public Analysis(Long id, Date dateCreated, String rssUrls, List<HotTopic> hotTopics) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.rssUrls = rssUrls;
        this.hotTopics = hotTopics;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getRssUrls() {
        return rssUrls;
    }

    public void setRssUrls(String rssUrls) {
        this.rssUrls = rssUrls;
    }

    public List<HotTopic> getHotTopics() {
        return hotTopics;
    }

    public void setHotTopics(List<HotTopic> hotTopics) {
        this.hotTopics = hotTopics;
    }
}
