package com.ljakovic.rssfeedanalyzer.util;

import com.apptasticsoftware.rssreader.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class KeywordUtil {

    @Value("${rss.feed.analyzer.stopwords}")
    private String stopwords;

    public List<String> getTopKeywords(final List<Item> rssFeedItems) {
        final Map<String, Integer> keywordFrequencyMap = rssFeedItems.stream()
                .flatMap(item -> {
                    final String itemContent = item.getTitle() + " " + item.getDescription();
                    return Arrays.stream(itemContent.toLowerCase()
                            .replaceAll("[^a-zA-Z ]", "")
                            .split("\\s+"));
                })
                .filter(word -> !Arrays.stream(stopwords.split(",")).toList().contains(word))
                .collect(Collectors.toMap(word -> word, word -> 1, Integer::sum));

        List<Map.Entry<String, Integer>> sortedList = keywordFrequencyMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .toList();

        return sortedList.stream().limit(3).map(Map.Entry::getKey).toList();
    }
}
