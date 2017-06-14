package org.woehlke.twitterwall.oodm.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name="entity")
public class MyEntities extends MyTwitterObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @JoinTable(name="entity_url")
    @ManyToMany(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER)
    private Set<MyUrlEntity> urls = new LinkedHashSet<MyUrlEntity>();

    @JoinTable(name="entity_hashtag")
    @ManyToMany(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER)
    private Set<MyHashTagEntity> tags = new LinkedHashSet<MyHashTagEntity>();

    @JoinTable(name="entity_mention")
    @ManyToMany(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER)
    private Set<MyMentionEntity> mentions = new LinkedHashSet<>();

    @JoinTable(name="entity_media")
    @ManyToMany(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER)
    private Set<MyMediaEntity> media = new LinkedHashSet<MyMediaEntity>();

    @JoinTable(name="entity_tickersymbol")
    @ManyToMany(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyEntities)) return false;
        if (!super.equals(o)) return false;

        MyEntities that = (MyEntities) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (urls != null ? !urls.equals(that.urls) : that.urls != null) return false;
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;
        if (mentions != null ? !mentions.equals(that.mentions) : that.mentions != null) return false;
        if (media != null ? !media.equals(that.media) : that.media != null) return false;
        return tickerSymbols != null ? tickerSymbols.equals(that.tickerSymbols) : that.tickerSymbols == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (urls != null ? urls.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (mentions != null ? mentions.hashCode() : 0);
        result = 31 * result + (media != null ? media.hashCode() : 0);
        result = 31 * result + (tickerSymbols != null ? tickerSymbols.hashCode() : 0);
        return result;
    }
}
