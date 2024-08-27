package com.ljakovic.rssfeedanalyzer.util;

import com.apptasticsoftware.rssreader.Item;
import com.ljakovic.rssfeedanalyzer.dto.KeywordDto;
import edu.stanford.nlp.simple.Sentence;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class KeywordUtil {

    public Map<String, HashSet<KeywordDto>> getFeedKeywords(final List<Item> rssFeedItems) {
        Map<String, HashSet<KeywordDto>> keywordMap = new HashMap<>();

        // Iterating over rssFeedItems
        for (Item item : rssFeedItems) {
            String channelTitle = item.getChannel().getTitle();

            // Process the content of the item to get the list of words (similar to original code)
            final Sentence sentence = new Sentence(item.getTitle().get());

            // Count occurrences of each word
            Map<String, Integer> wordCountMap = new HashMap<>();
            List<String> words = sentence.words();
            for (int i = 0; i < words.size(); i++) {
                String word = words.get(i).toLowerCase();
                String posTag = sentence.posTag(i);

                if (posTag.startsWith("NN") || posTag.startsWith("NNP") || posTag.startsWith("NNS") || posTag.startsWith("NNPS")) {
                    wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
                }
            }

            // Convert to a set of KeywordDto
            Set<KeywordDto> keywordSet = new HashSet<>();
            for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
                keywordSet.add(new KeywordDto(entry.getKey(), entry.getValue(), item));
            }
            HashSet<KeywordDto> set = keywordMap.get(channelTitle);

            if (set == null) {
                keywordMap.put(channelTitle, new HashSet<>());
            } else {
                HashSet<KeywordDto> combined = Stream.concat(set.stream(), keywordSet.stream())
                        .collect(Collectors.toCollection(HashSet::new));
                keywordMap.put(channelTitle, combined);
            }
        }
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
}
