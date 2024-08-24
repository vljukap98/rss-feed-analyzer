package com.ljakovic.rssfeedanalyzer.service;

import com.apptasticsoftware.rssreader.Item;
import com.ljakovic.rssfeedanalyzer.dto.KeywordDto;
import com.ljakovic.rssfeedanalyzer.exception.RssUrlsSizeException;
import com.ljakovic.rssfeedanalyzer.model.Analysis;
import com.ljakovic.rssfeedanalyzer.model.Article;
import com.ljakovic.rssfeedanalyzer.model.HotTopic;
import com.ljakovic.rssfeedanalyzer.repository.AnalysisRepository;
import com.ljakovic.rssfeedanalyzer.util.KeywordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

@Service
public class AnalysisService {

    private final RssFeedService rssFeedService;
    private final KeywordUtil keywordUtil;
    private final AnalysisRepository analysisRepo;

    @Autowired
    public AnalysisService(RssFeedService rssFeedService, KeywordUtil keywordUtil, AnalysisRepository analysisRepo) {
        this.rssFeedService = rssFeedService;
        this.keywordUtil = keywordUtil;
        this.analysisRepo = analysisRepo;
    }

    public Long analyse(final List<String> rssUrls) {

        if (rssUrls.size() < 2) {
            throw new RssUrlsSizeException("You need to pass at least 2 rss feed urls.");
        }

        final List<Item> rssItems = rssFeedService.getRssFeedItems(rssUrls);
        final Map<String, SortedSet<KeywordDto>> hotTopics = keywordUtil.getFeedKeywords(rssItems);

        final Analysis analysis = new Analysis();
        analysis.setDateCreated(new Date());
        analysis.setRssUrls(String.join(";;", rssUrls));
        analysis.setHotTopics(mapHotTopics(hotTopics));

        return analysisRepo.save(analysis).getId();
    }

    private List<HotTopic> mapHotTopics(Map<String, SortedSet<KeywordDto>> feedHotTopics) {
        return null;
    }

    private List<Article> mapArticles(Map<String, SortedSet<KeywordDto>> hotTopics) {
        return null;
    }
}
