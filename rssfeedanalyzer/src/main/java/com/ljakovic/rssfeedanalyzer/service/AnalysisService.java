package com.ljakovic.rssfeedanalyzer.service;

import com.apptasticsoftware.rssreader.Item;
import com.ljakovic.rssfeedanalyzer.dto.KeywordDto;
import com.ljakovic.rssfeedanalyzer.exception.RssUrlsSizeException;
import com.ljakovic.rssfeedanalyzer.model.Analysis;
import com.ljakovic.rssfeedanalyzer.model.Article;
import com.ljakovic.rssfeedanalyzer.model.HotTopic;
import com.ljakovic.rssfeedanalyzer.repository.AnalysisRepository;
import com.ljakovic.rssfeedanalyzer.repository.ArticleRepository;
import com.ljakovic.rssfeedanalyzer.repository.HotTopicRepository;
import com.ljakovic.rssfeedanalyzer.util.KeywordUtil;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalysisService {

    private final RssFeedService rssFeedService;
    private final KeywordUtil keywordUtil;
    private final AnalysisRepository analysisRepo;
    private final ArticleRepository articleRepo;
    private final HotTopicRepository hotTopicRepo;

    public AnalysisService(RssFeedService rssFeedService, KeywordUtil keywordUtil, AnalysisRepository analysisRepo, ArticleRepository articleRepo, HotTopicRepository hotTopicRepo) {
        this.rssFeedService = rssFeedService;
        this.keywordUtil = keywordUtil;
        this.analysisRepo = analysisRepo;
        this.articleRepo = articleRepo;
        this.hotTopicRepo = hotTopicRepo;
    }

    public Long analyse(final List<String> rssUrls) {

        if (rssUrls.size() < 2) {
            throw new RssUrlsSizeException("You need to pass at least 2 rss feed urls.");
        }

        final List<Item> rssItems = rssFeedService.getRssFeedItems(rssUrls);
        final Map<String, HashSet<KeywordDto>> hotTopics = keywordUtil.getFeedKeywords(rssItems);

        final Analysis analysis = new Analysis();
        analysis.setDateCreated(new Date());
        analysis.setRssUrls(String.join(";;", rssUrls));
        analysis.setHotTopics(mapHotTopics(hotTopics));

        final Analysis analysis1 = analysisRepo.save(analysis);

        return analysis1.getId();
    }

    private List<HotTopic> mapHotTopics(Map<String, HashSet<KeywordDto>> feedHotTopics) {
        final List<HotTopic> hotTopicList = new ArrayList<>();
        feedHotTopics.forEach((k,v) -> {
            v.forEach(kw -> {
                    final HotTopic hotTopic = new HotTopic();
                    hotTopic.setName(kw.word());
                    hotTopic.setOccurrences(kw.occurrences());
                    final Article article = mapArticle(kw.item());

                    hotTopic.setArticle(article);
                    hotTopicRepo.save(hotTopic);
                    hotTopicList.add(hotTopic);
            });

        });
        return hotTopicList;
    }

    private Article mapArticle(Item item) {
        final Article article = new Article();
        if (item.getTitle().isPresent()) {
            article.setTitle(item.getTitle().get());
        }
        if (item.getDescription().isPresent()) {
            //article.setDescription(item.getDescription().get());
        }
        if (item.getLink().isPresent()) {
            article.setLink(item.getLink().get());
        }
        return articleRepo.save(article);
    }
}
