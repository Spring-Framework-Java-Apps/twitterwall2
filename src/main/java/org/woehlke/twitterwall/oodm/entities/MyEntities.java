package org.woehlke.twitterwall.oodm.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tw on 10.06.17.
 */
@Entity
public class MyEntities extends MyTwitterObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<MyUrlEntity> urls = new LinkedList<MyUrlEntity>();

    @OneToMany
    private List<MyHashTagEntity> tags = new LinkedList<MyHashTagEntity>();

    @OneToMany
    private List<MyMentionEntity> mentions = new LinkedList<MyMentionEntity>();

    @OneToMany
    private List<MyMediaEntity> media = new LinkedList<MyMediaEntity>();

    @OneToMany
    private List<MyTickerSymbolEntity> tickerSymbols = new LinkedList<MyTickerSymbolEntity>();

    public MyEntities(List<MyUrlEntity> urls, List<MyHashTagEntity> tags, List<MyMentionEntity> mentions, List<MyMediaEntity> media) {
        this.urls = urls;
        this.tags = tags;
        this.mentions = mentions;
        this.media = media;
    }

    public MyEntities(List<MyUrlEntity> urls, List<MyHashTagEntity> tags, List<MyMentionEntity> mentions, List<MyMediaEntity> media, List<MyTickerSymbolEntity> tickerSymbols) {
        this(urls, tags, mentions, media);
        this.tickerSymbols = tickerSymbols;
    }

    private MyEntities() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MyUrlEntity> getUrls() {
        return urls;
    }

    public void setUrls(List<MyUrlEntity> urls) {
        this.urls = urls;
    }

    public List<MyHashTagEntity> getTags() {
        return tags;
    }

    public void setTags(List<MyHashTagEntity> tags) {
        this.tags = tags;
    }

    public List<MyMentionEntity> getMentions() {
        return mentions;
    }

    public void setMentions(List<MyMentionEntity> mentions) {
        this.mentions = mentions;
    }

    public List<MyMediaEntity> getMedia() {
        return media;
    }

    public void setMedia(List<MyMediaEntity> media) {
        this.media = media;
    }

    public List<MyTickerSymbolEntity> getTickerSymbols() {
        return tickerSymbols;
    }

    public void setTickerSymbols(List<MyTickerSymbolEntity> tickerSymbols) {
        this.tickerSymbols = tickerSymbols;
    }


}
