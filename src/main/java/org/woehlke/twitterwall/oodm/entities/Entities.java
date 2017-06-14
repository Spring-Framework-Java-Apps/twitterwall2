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
    @GeneratedValue
    private Long id;

    @JoinTable(name="entity_url")
    @ManyToMany(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER)
    private Set<Url> urls = new LinkedHashSet<Url>();

    @JoinTable(name="entity_hashtag")
    @ManyToMany(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER)
    private Set<HashTag> tags = new LinkedHashSet<HashTag>();

    @JoinTable(name="entity_mention")
    @ManyToMany(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER)
    private Set<Mention> mentions = new LinkedHashSet<>();

    @JoinTable(name="entity_media")
    @ManyToMany(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER)
    private Set<Media> media = new LinkedHashSet<Media>();

    @JoinTable(name="entity_tickersymbol")
    @ManyToMany(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER)
    private Set<TickerSymbol> tickerSymbols = new LinkedHashSet<TickerSymbol>();

    public Entities(Set<Url> urls, Set<HashTag> tags, Set<Mention> mentions, Set<Media> media) {
        this.urls = urls;
        this.tags = tags;
        this.mentions=mentions;
        this.media=media;
    }

    public Entities(Set<Url> urls, Set<HashTag> tags, Set<Mention> mentions, Set<Media> media, Set<TickerSymbol> tickerSymbols) {
        this(urls, tags, mentions, media);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entities)) return false;
        if (!super.equals(o)) return false;

        Entities that = (Entities) o;

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
