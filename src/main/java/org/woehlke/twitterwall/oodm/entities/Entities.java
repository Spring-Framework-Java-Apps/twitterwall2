package org.woehlke.twitterwall.oodm.entities;

import org.woehlke.twitterwall.oodm.entities.entities.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name="entity")
public class Entities extends AbstractTwitterObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique=true)
    private long idTwitterFromTweet;

    @JoinTable(name="entity_url")
    @ManyToMany(fetch=FetchType.EAGER)
    private Set<Url> urls = new LinkedHashSet<Url>();

    @JoinTable(name="entity_hashtag")
    @ManyToMany(fetch=FetchType.EAGER)
    private Set<HashTag> tags = new LinkedHashSet<HashTag>();

    @JoinTable(name="entity_mention")
    @ManyToMany(fetch=FetchType.EAGER)
    private Set<Mention> mentions = new LinkedHashSet<>();

    @JoinTable(name="entity_media")
    @ManyToMany(fetch=FetchType.EAGER)
    private Set<Media> media = new LinkedHashSet<Media>();

    @JoinTable(name="entity_tickersymbol")
    @ManyToMany(fetch=FetchType.EAGER)
    private Set<TickerSymbol> tickerSymbols = new LinkedHashSet<TickerSymbol>();

    public Entities(Set<Url> urls, Set<HashTag> tags, Set<Mention> mentions, Set<Media> media,long idTwitterFromTweet) {
        this.urls = urls;
        this.tags = tags;
        this.mentions=mentions;
        this.media=media;
        this.idTwitterFromTweet = idTwitterFromTweet;
    }

    public Entities(Set<Url> urls, Set<HashTag> tags, Set<Mention> mentions, Set<Media> media, long idTwitterFromTweet, Set<TickerSymbol> tickerSymbols) {
        this(urls, tags, mentions, media, idTwitterFromTweet);
        this.tickerSymbols = tickerSymbols;
    }

    private Entities() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Url> getUrls() {
        return urls;
    }

    public void setUrls(Set<Url> urls) {
        this.urls = urls;
    }

    public Set<HashTag> getTags() {
        return tags;
    }

    public void setTags(Set<HashTag> tags) {
        this.tags = tags;
    }

    public Set<Mention> getMentions() {
        return mentions;
    }

    public void setMentions(Set<Mention> mentions) {
        this.mentions = mentions;
    }

    public Set<Media> getMedia() {
        return media;
    }

    public void setMedia(Set<Media> media) {
        this.media = media;
    }

    public Set<TickerSymbol> getTickerSymbols() {
        return tickerSymbols;
    }

    public void setTickerSymbols(Set<TickerSymbol> tickerSymbols) {
        this.tickerSymbols = tickerSymbols;
    }

    public long getIdTwitterFromTweet() {
        return idTwitterFromTweet;
    }

    public void setIdTwitterFromTweet(long idTwitterFromTweet) {
        this.idTwitterFromTweet = idTwitterFromTweet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entities)) return false;
        if (!super.equals(o)) return false;

        Entities entities = (Entities) o;

        return idTwitterFromTweet == entities.idTwitterFromTweet;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (idTwitterFromTweet ^ (idTwitterFromTweet >>> 32));
        return result;
    }
}
