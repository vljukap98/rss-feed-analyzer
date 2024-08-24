package com.ljakovic.rssfeedanalyzer.repository;

import com.ljakovic.rssfeedanalyzer.model.HotTopic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotTopicRepository extends JpaRepository<HotTopic, Long> {
}
