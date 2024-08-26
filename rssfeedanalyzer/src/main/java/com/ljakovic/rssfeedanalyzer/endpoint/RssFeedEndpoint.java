package com.ljakovic.rssfeedanalyzer.endpoint;

import com.apptasticsoftware.rssreader.Item;
import com.ljakovic.rssfeedanalyzer.model.Analysis;
import com.ljakovic.rssfeedanalyzer.model.Article;
import com.ljakovic.rssfeedanalyzer.repository.AnalysisRepository;
import com.ljakovic.rssfeedanalyzer.repository.ArticleRepository;
import com.ljakovic.rssfeedanalyzer.service.AnalysisService;
import com.ljakovic.rssfeedanalyzer.service.RssFeedService;
import com.ljakovic.rssfeedanalyzer.util.KeywordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/rss")
public class RssFeedEndpoint {

    private final AnalysisService analysisService;
    private final AnalysisRepository articleRepo;

    public RssFeedEndpoint(AnalysisService analysisService, AnalysisRepository articleRepo) {
        this.analysisService = analysisService;
        this.articleRepo = articleRepo;
    }

    @PostMapping(
            value = "/analyse/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Long> getHotTopics(@RequestBody List<String> rssUrls) {

        return ResponseEntity.ok(analysisService.analyse(rssUrls));
    }

    @GetMapping(
            value = "/frequency/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Analysis> getTopArticles(@PathVariable Long id) {
        return ResponseEntity.ok(articleRepo.findById(id).orElse(null));
    }
}
