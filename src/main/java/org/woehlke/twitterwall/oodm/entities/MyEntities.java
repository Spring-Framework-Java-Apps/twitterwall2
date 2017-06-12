package org.woehlke.twitterwall.oodm.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by tw on 10.06.17.
 */
@Entity
public class MyEntities extends MyTwitterObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER)
    private Set<MyUrlEntity> urls = new LinkedHashSet<MyUrlEntity>();

    @OneToMany(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER)
    private Set<MyHashTagEntity> tags = new LinkedHashSet<MyHashTagEntity>();

    @ManyToMany(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER)
    private Set<MyMentionEntity> mentions = new LinkedHashSet<>();

    @ManyToMany(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER)
    private Set<MyMediaEntity> media = new LinkedHashSet<MyMediaEntity>();

    @OneToMany(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER)
    private Set<MyTickerSymbolEntity> tickerSymbols = new LinkedHashSet<MyTickerSymbolEntity>();

    public MyEntities(Set<MyUrlEntity> urls, Set<MyHashTagEntity> tags, Set<MyMentionEntity> mentions, Set<MyMediaEntity> media) {
        this.urls = urls;
        this.tags = tags;
        this.mentions=mentions;
        this.media=media;
    }

    public MyEntities(Set<MyUrlEntity> urls, Set<MyHashTagEntity> tags, Set<MyMentionEntity> mentions, Set<MyMediaEntity> media, Set<MyTickerSymbolEntity> tickerSymbols) {
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

    public Set<MyUrlEntity> getUrls() {
        return urls;
    }

    public void setUrls(Set<MyUrlEntity> urls) {
        this.urls = urls;
    }

    public Set<MyHashTagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<MyHashTagEntity> tags) {
        this.tags = tags;
    }

    public Set<MyMentionEntity> getMentions() {
        return mentions;
    }

    public void setMentions(Set<MyMentionEntity> mentions) {
        this.mentions = mentions;
    }

    public Set<MyMediaEntity> getMedia() {
        return media;
    }

    public void setMedia(Set<MyMediaEntity> media) {
        this.media = media;
    }

    public Set<MyTickerSymbolEntity> getTickerSymbols() {
        return tickerSymbols;
    }

    public void setTickerSymbols(Set<MyTickerSymbolEntity> tickerSymbols) {
        this.tickerSymbols = tickerSymbols;
    }


}
