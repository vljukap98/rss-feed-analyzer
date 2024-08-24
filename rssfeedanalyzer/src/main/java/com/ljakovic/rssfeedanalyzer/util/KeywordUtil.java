package com.ljakovic.rssfeedanalyzer.util;

import com.apptasticsoftware.rssreader.Item;
import com.ljakovic.rssfeedanalyzer.dto.KeywordDto;
import edu.stanford.nlp.simple.Sentence;
import org.springframework.stereotype.Component;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
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
        Map<String, SortedSet<KeywordDto>> keywordMap = rssFeedItems.stream()
                .collect(Collectors.groupingBy(
                        item -> item.getChannel().getTitle(),
                        Collectors.flatMapping(
                                item -> {
                                    final String itemDescription = parseHtml(String.valueOf(item.getDescription()));
                                    final String itemContent = item.getTitle() + " " + itemDescription;
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

                                    return wordCountMap.entrySet().stream()
                                            .map(entry -> new KeywordDto(entry.getKey(), entry.getValue(), item));
                                },
                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingInt(KeywordDto::occurrences).reversed()))
                        )
                ));
        // Create a frequency map for all keywords across the channel sets
        Map<String, Integer> keywordFrequencyMap = new HashMap<>();

        for (Set<KeywordDto> keywordSet : keywordMap.values()) {
            for (KeywordDto keywordDto : keywordSet) {
                keywordFrequencyMap.put(keywordDto.word(), keywordFrequencyMap.getOrDefault(keywordDto.word(), 0) + 1);
            }
        }

        keywordMap.forEach((key, keywordSet) -> keywordSet.removeIf(keywordDto -> keywordFrequencyMap.getOrDefault(keywordDto.word(), 0) < 2));

        return keywordMap;
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

    private String parseHtml(final String html) {
        return Jsoup.parse(html).text();
    }
}
