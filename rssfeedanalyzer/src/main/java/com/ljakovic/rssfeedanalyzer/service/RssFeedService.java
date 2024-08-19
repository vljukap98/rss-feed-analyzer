package com.ljakovic.rssfeedanalyzer.service;

import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RssFeedService {

    public List<Item> getRssFeedItems(final List<String> rssUrls) {
        final RssReader reader = new RssReader();
        return reader.read(rssUrls)
                .toList();
    }
}
