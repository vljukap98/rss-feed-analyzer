package com.ljakovic.rssfeedanalyzer.dto;

import com.apptasticsoftware.rssreader.Item;

import java.util.Comparator;

public record KeywordDto (String word, Integer occurrences, Item item) implements Comparator<KeywordDto> {

    @Override
    public int compare(KeywordDto kw1, KeywordDto kw2) {
        return Integer.compare(kw1.occurrences, kw2.occurrences); // Sort by count in descending order

    }
}
