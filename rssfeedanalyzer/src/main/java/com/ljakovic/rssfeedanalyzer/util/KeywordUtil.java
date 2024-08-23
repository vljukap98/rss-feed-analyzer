package com.ljakovic.rssfeedanalyzer.util;

import com.apptasticsoftware.rssreader.Item;
import com.ljakovic.rssfeedanalyzer.dto.KeywordDto;
import edu.stanford.nlp.simple.Sentence;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class KeywordUtil {

    private static final Pattern URL_PATTERN = Pattern.compile(
            "https?://[\\w/:%#$&?()~.=\\+\\-]+|www\\.[\\w/:%#$&?()~.=\\+\\-]+", Pattern.CASE_INSENSITIVE);
    private static final Pattern UNWANTED_SYMBOLS_PATTERN = Pattern.compile(
            "[#%]", Pattern.CASE_INSENSITIVE);
    private static final Pattern NON_ALPHANUMERIC_PATTERN = Pattern.compile(
            "[^a-zA-Z\\s]", Pattern.CASE_INSENSITIVE);
    private static final Pattern SPECIFIC_PATTERNS_PATTERN = Pattern.compile(
            "(nbsp|font|target|color)[^\\s]*", Pattern.CASE_INSENSITIVE);

    public Map<String, SortedSet<KeywordDto>> getFeedKeywords(final List<Item> rssFeedItems) {
        return rssFeedItems.stream()
                .collect(Collectors.groupingBy(
                        item -> item.getChannel().getTitle(),
                        Collectors.flatMapping(
                                item -> {
                                    final String itemContent = item.getTitle() + " " + item.getDescription();
                                    final String cleanContent = cleanText(itemContent);
                                    final Sentence sentence = new Sentence(cleanContent);

                                    // Count occurrences of each word
                                    Map<String, Integer> wordCountMap = sentence.words().stream()
                                            .filter(word -> sentence.posTag(sentence.words().indexOf(word)).startsWith("NN"))
                                            .map(String::toLowerCase)
                                            .collect(Collectors.toMap(
                                                    word -> word,
                                                    word -> 1,
                                                    Integer::sum
                                            ));

                                    // Convert to a stream of KeywordDto and collect into a TreeSet
                                    return wordCountMap.entrySet().stream()
                                            .map(entry -> new KeywordDto(entry.getKey(), entry.getValue(), item));
                                },
                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingInt(KeywordDto::occurrences).reversed()))
                        )
                ));
    }

    private String cleanText(String text) {
        // Remove URLs
        text = URL_PATTERN.matcher(text).replaceAll("");
        // Remove unwanted symbols
        text = UNWANTED_SYMBOLS_PATTERN.matcher(text).replaceAll("");
        // Remove non-alphanumeric characters except spaces
        text = NON_ALPHANUMERIC_PATTERN.matcher(text).replaceAll("");
        // Remove specific unwanted patterns
        text = SPECIFIC_PATTERNS_PATTERN.matcher(text).replaceAll("");
        // Remove extra whitespace
        text = text.trim().replaceAll("\\s+", " ");
        return text;
    }
}
