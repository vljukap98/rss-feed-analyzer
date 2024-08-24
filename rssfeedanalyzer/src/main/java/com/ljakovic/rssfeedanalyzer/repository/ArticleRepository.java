package com.ljakovic.rssfeedanalyzer.repository;

import com.ljakovic.rssfeedanalyzer.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
