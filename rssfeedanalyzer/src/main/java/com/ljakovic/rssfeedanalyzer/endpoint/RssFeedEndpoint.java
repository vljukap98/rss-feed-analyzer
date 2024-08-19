package com.ljakovic.rssfeedanalyzer.endpoint;

import com.apptasticsoftware.rssreader.Item;
import com.ljakovic.rssfeedanalyzer.service.RssFeedService;
import com.ljakovic.rssfeedanalyzer.util.KeywordUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
            value = "/analyse/new"
    )
    public ResponseEntity<List<String>> getHotTopics() {
        List<Item> rssItems = rssFeedService.getRssFeedItems(
                List.of("https://news.google.com/news?cf=all&hl=en&pz=1&ned=us&output=rss"));
        return ResponseEntity.ok(keywordUtil.getTopKeywords(rssItems));
    }
}
