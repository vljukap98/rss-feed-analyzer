package com.ljakovic.rssfeedanalyzer.endpoint;

import com.apptasticsoftware.rssreader.Item;
import com.ljakovic.rssfeedanalyzer.service.AnalysisService;
import com.ljakovic.rssfeedanalyzer.service.RssFeedService;
import com.ljakovic.rssfeedanalyzer.util.KeywordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/rss")
public class RssFeedEndpoint {

    private final AnalysisService analysisService;

    @Autowired
    public RssFeedEndpoint(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }
    @PostMapping(
            value = "/analyse/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Long> getHotTopics(@RequestBody List<String> rssUrls) {

        return ResponseEntity.ok(analysisService.analyse(rssUrls));
    }
}
