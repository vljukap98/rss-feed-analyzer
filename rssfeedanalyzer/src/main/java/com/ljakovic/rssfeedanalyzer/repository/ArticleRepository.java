package com.ljakovic.rssfeedanalyzer.repository;

import com.ljakovic.rssfeedanalyzer.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT ar " +
            "FROM Article ar " +
            "LEFT JOIN HotTopic ht ON ht.id = ar.hotTopic.id " +
            "LEFT JOIN Analysis an ON an.id = ht.analysis.id " +
            "WHERE an.id = ?1 " +
            "ORDER BY ht.occurrences DESC")
    List<Article> findTopArticlesByAnalysis(Long analysisId);
}
