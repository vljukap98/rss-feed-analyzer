package com.ljakovic.rssfeedanalyzer.repository;

import com.ljakovic.rssfeedanalyzer.model.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
}
