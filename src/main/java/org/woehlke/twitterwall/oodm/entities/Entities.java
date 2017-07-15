package org.woehlke.twitterwall.oodm.entities;

import org.woehlke.twitterwall.oodm.entities.common.EntitiesFilter;
import org.woehlke.twitterwall.oodm.entities.entities.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by tw on 11.07.17.
 */
@Embeddable
public class Entities extends EntitiesFilter implements Serializable {

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<Url> urls = new LinkedHashSet<Url>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<HashTag> tags = new LinkedHashSet<HashTag>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<Mention> mentions = new LinkedHashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<Media> media = new LinkedHashSet<Media>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<TickerSymbol> tickerSymbols = new LinkedHashSet<TickerSymbol>();

    public Entities(Set<Url> urls, Set<HashTag> tags, Set<Mention> mentions, Set<Media> media, Set<TickerSymbol> tickerSymbols) {
        this.urls = urls;
        this.tags = tags;
        this.mentions = mentions;
        this.media = media;
        this.tickerSymbols = tickerSymbols;
    }

    public Entities() {
    }

    public Set<Url> getUrls() {
        return urls;
    }

    public void setUrls(Set<Url> urls) {
        this.urls.clear();
        this.urls.addAll(urls);
    }

    public boolean addAllUrls(Set<Url> urls) {
        boolean result = false;
        for(Url url:urls){
            if((url != null) && (!this.urls.contains(url))){
                this.urls.add(url);
                result = true;
            }
        }
        return result;
    }

    public boolean removeAllUrls(Set<Url> urls) {
        boolean result = false;
        for(Url url:urls){
            if((url != null) && (this.urls.contains(url))){
                this.urls.remove(url);
                result = true;
            }
        }
        return result;
    }

    public boolean removeAllUrls() {
        this.urls.clear();
        return this.urls.isEmpty();
    }

    public boolean addUrl(Url url) {
        if((url != null) && (!this.urls.contains(url))){
            return this.urls.add(url);
        } else {
            return false;
        }
    }

    public boolean removetUrl(Url url) {
        if((url != null) && (this.urls.contains(url))){
            return this.urls.remove(url);
        } else {
            return false;
        }
    }

    public Set<HashTag> getTags() {
        return tags;
    }

    public void setTags(Set<HashTag> tags) {
        this.tags.clear();
        this.tags.addAll(tags);
    }

    public boolean addAllTags(Set<HashTag> tags) {
        boolean result = false;
        for(HashTag tag:tags){
            if((tag != null) && (!this.tags.contains(tag))){
                this.tags.add(tag);
                result = true;
            }
        }
        return result;
    }

    public boolean removeAllTags(Set<HashTag> tags) {
        boolean result = false;
        for(HashTag tag:tags){
            if((tag != null) && (this.tags.contains(tag))){
                this.tags.remove(tag);
                result = true;
            }
        }
        return result;
    }

    public boolean removeAllTags() {
        this.tags.clear();
        return this.tags.isEmpty();
    }

    public boolean addTag(HashTag tag) {
        if((tag != null) && (!this.tags.contains(tag))){
            return this.tags.add(tag);
        } else {
            return false;
        }
    }

    public boolean removeTag(HashTag tag) {
        if((tag != null) && (this.tags.contains(tag))){
            return this.tags.remove(tag);
        } else {
            return false;
        }
    }


    public Set<Mention> getMentions() {
        return mentions;
    }

    public void setMentions(Set<Mention> mentions) {
        this.mentions.clear();
        this.mentions.addAll(mentions);
    }

    public boolean addAllMentions(Set<Mention> mentions) {
        boolean result = false;
        for(Mention mention:mentions){
            if((mention != null) && (!this.mentions.contains(mention))){
                this.mentions.add(mention);
                result = true;
            }
        }
        return result;
    }

    public boolean removeAllMentions(Set<Mention> mentions) {
        boolean result = false;
        for(Mention mention:mentions){
            if((mention != null) && (this.mentions.contains(mention))){
                this.mentions.remove(mention);
                result = true;
            }
        }
        return result;
    }

    public boolean removeAllMentions() {
        this.mentions.clear();
        return this.mentions.isEmpty();
    }

    public boolean addMention(Mention mention) {
        return this.mentions.add(mention);
    }

    public boolean removeMention(Mention mention) {
        return this.mentions.remove(mention);
    }


    public Set<Media> getMedia() {
        return media;
    }

    public void setMedia(Set<Media> media) {
        this.media.clear();
        this.media.addAll(media);
    }

    public boolean addAllMedia(Set<Media> media) {
        boolean result = false;
        for(Media medium:media){
            if((medium != null) && (!this.media.contains(medium))){
                this.media.add(medium);
                result = true;
            }
        }
        return result;
    }

    public boolean removeAllMedia(Set<Media> media) {
        boolean result = false;
        for(Media medium:media){
            if((medium != null) && (this.media.contains(medium))){
                this.media.remove(medium);
                result = true;
            }
        }
        return result;
    }

    public boolean removeAllMedia() {
        this.media.clear();
        return this.media.isEmpty();
    }

    public boolean addMedium(Media medium) {
        if (medium == null) {
            return false;
        } else {
            return this.media.add(medium);
        }
    }

    public boolean removeMedium(Media medium) {
        return this.media.remove(medium);
    }

    public Set<TickerSymbol> getTickerSymbols() {
        return tickerSymbols;
    }

    public void setTickerSymbols(Set<TickerSymbol> tickerSymbols) {
        this.tickerSymbols.clear();
        this.tickerSymbols.addAll(tickerSymbols);
    }

    public boolean addAllTickerSymbols(Set<TickerSymbol> tickerSymbols) {
        boolean result = false;
        for(TickerSymbol tickerSymbol:tickerSymbols){
            if((tickerSymbol != null) && (!this.tickerSymbols.contains(tickerSymbol))){
                this.tickerSymbols.add(tickerSymbol);
                result = true;
            }
        }
        return result;
    }

    public boolean removeAllTickerSymbols(Set<TickerSymbol> tickerSymbols) {
        boolean result = false;
        for(TickerSymbol tickerSymbol:tickerSymbols){
            if((tickerSymbol != null) && (this.tickerSymbols.contains(tickerSymbol))){
                this.tickerSymbols.remove(tickerSymbol);
                result = true;
            }
        }
        return result;
    }

    public boolean removeAllTickerSymbols() {
        this.tickerSymbols.clear();
        return this.tickerSymbols.isEmpty();
    }

    public boolean addTickerSymbol(TickerSymbol tickerSymbol) {
        return this.tickerSymbols.add(tickerSymbol);
    }

    public boolean removeTickerSymbol(TickerSymbol tickerSymbol) {
        return this.tickerSymbols.remove(tickerSymbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entities)) return false;

        Entities entities = (Entities) o;

        if (!urls.equals(entities.urls)) return false;
        if (!tags.equals(entities.tags)) return false;
        if (!mentions.equals(entities.mentions)) return false;
        if (!media.equals(entities.media)) return false;
        return tickerSymbols.equals(entities.tickerSymbols);
    }

    @Override
    public int hashCode() {
        int result = urls.hashCode();
        result = 31 * result + tags.hashCode();
        result = 31 * result + mentions.hashCode();
        result = 31 * result + media.hashCode();
        result = 31 * result + tickerSymbols.hashCode();
        return result;
    }

    private String toStringUrls(){
        StringBuffer myUrls = new StringBuffer();
        myUrls.append("[ ");
        for (Url url : urls) {
            if(url != null) {
                myUrls.append(url.toString());
                myUrls.append(", ");
            } else {
                myUrls.append(", null");
            }
        }
        myUrls.append(" ]");
        return myUrls.toString();
    }

    private String toStringHashTags(){
        StringBuffer myTags = new StringBuffer();
        myTags.append("[ ");
        for (HashTag tag : tags) {
            if(tag != null){
                myTags.append(tag.toString());
                myTags.append(", ");
            } else {
                myTags.append(", null");
            }
        }
        myTags.append(" ]");
        return myTags.toString();
    }

    private String toStringMentions(){
        StringBuffer myMentions = new StringBuffer();
        myMentions.append("[ ");
        for (Mention mention : mentions) {
            if(mention != null){
                myMentions.append(mention.toString());
                myMentions.append(", ");
            } else {
                myMentions.append(", null");
            }
        }
        myMentions.append(" ]");
        return myMentions.toString();
    }

    private String toStringMedia(){
        StringBuffer myMedia = new StringBuffer();
        myMedia.append("[ ");
        for (Media medium : media) {
            if(medium != null){
                myMedia.append(medium.toString());
                myMedia.append(", ");
            } else {
                myMedia.append(", null");
            }
        }
        myMedia.append(" ]");
        return myMedia.toString();
    }

    private String toStringTickerSymbols(){
        StringBuffer myTickerSymbols = new StringBuffer();
        myTickerSymbols.append("[ ");
        for (TickerSymbol tickerSymbol : tickerSymbols) {
            if(tickerSymbol != null){
                myTickerSymbols.append(tickerSymbol.toString());
                myTickerSymbols.append(", ");
            } else {
                myTickerSymbols.append(", null");
            }
        }
        myTickerSymbols.append(" ]");
        return myTickerSymbols.toString();
    }

    @Override
    public String toString() {
        return "Entities{" +
            ",\n urls=" + toStringUrls() +
            ",\n tags=" + toStringHashTags() +
            ",\n mentions=" + toStringMentions() +
            ",\n media=" +toStringMedia() +
            ",\n tickerSymbols=" +toStringTickerSymbols() +
            "\n}";
    }

    public String getFormattedText(String formattedText ) {

        formattedText = getFormattedTextForUserProfiles(formattedText);

        Set<HashTag> tags = this.getTags();
        formattedText = getFormattedTextForHashTags(tags,formattedText);

        Set<Media> media = this.getMedia();
        formattedText = getFormattedTextForMedia(media, formattedText);

        Set<Url> urls = this.getUrls();
        formattedText = getFormattedTextForUrls(urls, formattedText);

        formattedText = getFormattedTextForUrls( formattedText );

        Set<Mention> mentions = this.getMentions();
        formattedText = super.getFormattedTextForMentions(mentions, formattedText);

        Set<TickerSymbol> tickerSymbols = this.getTickerSymbols();
        formattedText = getFormattedTextForTickerSymbols(tickerSymbols, formattedText);

        return formattedText;
    }
}
