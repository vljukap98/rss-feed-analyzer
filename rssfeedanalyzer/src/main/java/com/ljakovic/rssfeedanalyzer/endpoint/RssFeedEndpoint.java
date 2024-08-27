package com.ljakovic.rssfeedanalyzer.endpoint;

import com.ljakovic.rssfeedanalyzer.model.Article;
import com.ljakovic.rssfeedanalyzer.repository.ArticleRepository;
import com.ljakovic.rssfeedanalyzer.service.AnalysisService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/rss")
public class RssFeedEndpoint {

    private final AnalysisService analysisService;
    private final ArticleRepository articleRepo;

    public RssFeedEndpoint(AnalysisService analysisService, ArticleRepository articleRepo1) {
        this.analysisService = analysisService;
        this.articleRepo = articleRepo1;
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
    public ResponseEntity<List<Article>> getTopArticles(@PathVariable Long id) {
        return ResponseEntity.ok(articleRepo.findTopArticlesByAnalysis(id).stream().limit(3).toList());
    }
}
