package com.ljakovic.rssfeedanalyzer.endpoint;

import com.apptasticsoftware.rssreader.Item;
import com.ljakovic.rssfeedanalyzer.service.RssFeedService;
import com.ljakovic.rssfeedanalyzer.util.KeywordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/rss")
public class RssFeedEndpoint {

    private final RssFeedService rssFeedService;
    private final KeywordUtil keywordUtil;


    @Autowired
    public RssFeedEndpoint(RssFeedService rssFeedService, KeywordUtil keywordUtil) {
        this.rssFeedService = rssFeedService;
        this.keywordUtil = keywordUtil;
    }
    @PostMapping(
            value = "/analyse/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<String>> getHotTopics(List<String> rssUrls) {
        List<Item> rssItems = rssFeedService.getRssFeedItems(rssUrls);
        keywordUtil.getFeedKeywords(rssItems).forEach((channel, keywords) -> {
            System.out.println("Channel: " + channel);
            System.out.println("Keywords: " + keywords);
        });
        return ResponseEntity.ok().build();
    }
}
