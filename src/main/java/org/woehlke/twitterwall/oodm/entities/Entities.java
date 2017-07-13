package org.woehlke.twitterwall.oodm.entities;

import org.woehlke.twitterwall.oodm.entities.entities.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 11.07.17.
 */
@Embeddable
public class Entities {

    //@JoinTable(name = "tweet_url")
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<Url> urls = new LinkedHashSet<Url>();

    //@JoinTable(name = "tweet_hashtag")
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<HashTag> tags = new LinkedHashSet<HashTag>();

    //@JoinTable(name = "tweet_mention")
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<Mention> mentions = new LinkedHashSet<>();

    //@JoinTable(name = "tweet_media")
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<Media> media = new LinkedHashSet<Media>();

    //@JoinTable(name = "tweet_tickersymbol")
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<TickerSymbol> tickerSymbols = new LinkedHashSet<TickerSymbol>();


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

    private String getFormattedTextForMentions(Set<Mention> mentions, String formattedText) {
        for (Mention mention : mentions) {

            Pattern userPattern = Pattern.compile("@(" + mention.getScreenName() + ")(" + stopChar + ")");
            Matcher m1 = userPattern.matcher(formattedText);
            formattedText = m1.replaceAll("<a class=\"tweet-action tweet-profile\" href=\"https://twitter.com/$1\" target=\"_blank\">@$1</a>$2");

            Pattern userPattern2 = Pattern.compile("@(" + mention.getScreenName() + ")$");
            Matcher m2 = userPattern2.matcher(formattedText);
            formattedText = m2.replaceAll("<a class=\"tweet-action tweet-profile\" href=\"https://twitter.com/$1\" target=\"_blank\">@$1</a> ");
        }
        return formattedText;
    }

    private String getFormattedTextForUserProfiles(String formattedText) {
        Pattern userPattern = Pattern.compile("@(\\w{1,15})(" + stopChar + ")");
        Matcher m1 = userPattern.matcher(formattedText);
        formattedText = m1.replaceAll("<a class=\"tweet-action tweet-profile\" href=\"/profile/$1\">@$1</a>$2");

        Pattern userPattern2 = Pattern.compile("@(\\w{1,15})$");
        Matcher m2 = userPattern2.matcher(formattedText);
        formattedText = m2.replaceAll("<a class=\"tweet-action tweet-profile\" href=\"/profile/$1\">@$1</a> ");

        return formattedText;
    }

    private String getFormattedTextForHashTags(Set<HashTag> tags, String formattedText) {
        for (HashTag tag : tags) {

            Pattern hashTagPattern = Pattern.compile("#(" + tag.getText() + ")(" + stopChar + ")");
            Matcher m3 = hashTagPattern.matcher(formattedText);
            formattedText = m3.replaceAll("<a class=\"tweet-action tweet-hashtag\" href=\"/hashtag/user/$1\">#$1</a>$2");

            Pattern hashTagPattern2 = Pattern.compile("#(" + tag.getText() + ")$");
            Matcher m4 = hashTagPattern2.matcher(formattedText);
            formattedText = m4.replaceAll("<a class=\"tweet-action tweet-hashtag\" href=\"/hashtag/user/$1\">#$1</a> ");
        }
        return formattedText;
    }

    private String getFormattedTextForTickerSymbols(Set<TickerSymbol> tickerSymbols, String formattedText) {
        for(TickerSymbol tickerSymbol:tickerSymbols){
            Pattern myUrl1 = Pattern.compile("(" + tickerSymbol.getUrl() + ")(" + stopChar + ")");
            Matcher m10 = myUrl1.matcher(formattedText);
            formattedText = m10.replaceAll("<br/><br/><a class=\"tweet-action tweet-photo\" href=\"" + tickerSymbol.getUrl() + "\" target=\"_blank\">"+tickerSymbol.getTickerSymbol()+"</a>$2");

            Pattern myUrl2 = Pattern.compile("(" + tickerSymbol.getUrl() + ")$");
            Matcher m11 = myUrl2.matcher(formattedText);
            formattedText = m11.replaceAll("<br/><br/><a class=\"tweet-action tweet-photo\" href=\"" + tickerSymbol.getUrl() + "\" target=\"_blank\">"+tickerSymbol.getTickerSymbol()+"</a> ");
        }
        return formattedText; //TODO: HIER WEIER
    }

    /*
    private String getFormattedTextForHashTagsInTweet(String formattedText) {
        Pattern hashTagPattern = Pattern.compile("#(\\w*)(" + stopChar + ")");
        Matcher m3 = hashTagPattern.matcher(formattedText);
        formattedText = m3.replaceAll("<a class=\"tweet-action tweet-hashtag\" href=\"/hashtag/tweet/$1\">#$1</a>$2");

        Pattern hashTagPattern2 = Pattern.compile("#(\\w*)$");
        Matcher m4 = hashTagPattern2.matcher(formattedText);
        formattedText = m4.replaceAll("<a class=\"tweet-action tweet-hashtag\" href=\"/hashtag/tweet/$1\">#$1</a> ");

        return formattedText;
    }*/

    private String getFormattedTextForMedia(Set<Media> media, String formattedText) {
        for (Media medium : media) {
            if (medium.getMediaType().compareTo("photo") == 0) {
                Pattern myUrl1 = Pattern.compile("(" + medium.getUrl() + ")(" + stopChar + ")");
                Matcher m10 = myUrl1.matcher(formattedText);
                formattedText = m10.replaceAll("<br/><br/><a class=\"tweet-action tweet-photo\" href=\"" + medium.getExpanded() + "\" target=\"_blank\"><img class=\"tweet-photo\" src=\"" + medium.getMediaHttps() + "\" /></a>$2");

                Pattern myUrl2 = Pattern.compile("(" + medium.getUrl() + ")$");
                Matcher m11 = myUrl2.matcher(formattedText);
                formattedText = m11.replaceAll("<br/><br/><a class=\"tweet-action tweet-photo\" href=\"" + medium.getExpanded() + "\" target=\"_blank\"><img class=\"tweet-photo\" src=\"" + medium.getMediaHttps() + "\" /></a> ");
            }
        }
        return formattedText;
    }

    public String getFormattedUrlForUrls(Set<Url> urls, String formattedText) {
        for (Url url : urls) {

            Pattern myUrl1 = Pattern.compile(url.getUrl());
            Matcher m10 = myUrl1.matcher(formattedText);
            formattedText = m10.replaceAll("<a href=\"" + url.getExpanded() + "\" class=\"tw-url3\" target=\"_blank\">" + url.getDisplay() + "</a>");

            /*
            Pattern myUrl1 = Pattern.compile("(" + url.getUrl() + ")(" + stopChar + ")");
            Matcher m10 = myUrl1.matcher(formattedText);
            formattedText = m10.replaceAll("<a href=\"" + url.getExpanded() + "\" class=\"tw-url3\" target=\"_blank\">" + url.getDisplay() + "</a>$2");

            Pattern myUrl4 = Pattern.compile("(" + url.getUrl() + ")$");
            Matcher m20 = myUrl4.matcher(formattedText);
            formattedText = m20.replaceAll("<a href=\"" + url.getExpanded() + "\" class=\"tw-url3\" target=\"_blank\">" + url.getDisplay() + "</a> ");
            */
        }
        return formattedText;
    }

    private String getFormattedTextForUrls(Set<Url> urls, String formattedText) {
        for (Url url : urls) {

            Pattern myUrl2 = Pattern.compile("(" + url.getDisplay() + ")(" + stopChar + ")");
            Matcher m11 = myUrl2.matcher(formattedText);
            formattedText = m11.replaceAll("<a href=\"" + url.getExpanded() + "\" class=\"tw-display1\" target=\"_blank\">" + url.getDisplay() + "</a>$2");

            Pattern myUrl5 = Pattern.compile("(" + url.getDisplay() + ")$");
            Matcher m21 = myUrl5.matcher(formattedText);
            formattedText = m21.replaceAll("<a href=\"" + url.getExpanded() + "\" class=\"tw-display2\" target=\"_blank\">" + url.getDisplay() + "</a> ");

            Pattern myUrl3 = Pattern.compile("(" + url.getExpanded() + ")(" + stopChar + ")");
            Matcher m12 = myUrl3.matcher(formattedText);
            formattedText = m12.replaceAll("<a href=\"" + url.getExpanded() + "\" class=\"tw-expanded1\" target=\"_blank\">" + url.getDisplay() + "</a>$2");

            Pattern myUrl6 = Pattern.compile("(" + url.getExpanded() + ")$");
            Matcher m22 = myUrl6.matcher(formattedText);
            formattedText = m22.replaceAll("<a href=\"" + url.getExpanded() + "\" class=\"tw-expanded2\" target=\"_blank\">" + url.getDisplay() + "</a> ");

            Pattern myUrl1 = Pattern.compile("(" + url.getUrl() + ")(" + stopChar + ")");
            Matcher m10 = myUrl1.matcher(formattedText);
            formattedText = m10.replaceAll("<a href=\"" + url.getExpanded() + "\" class=\"tw-url1\" target=\"_blank\">" + url.getDisplay() + "</a>$2");

            Pattern myUrl4 = Pattern.compile("(" + url.getUrl() + ")$");
            Matcher m20 = myUrl4.matcher(formattedText);
            formattedText = m20.replaceAll("<a href=\"" + url.getExpanded() + "\" class=\"tw-url2\" target=\"_blank\">" + url.getDisplay() + "</a> ");

        }
        return formattedText;
    }

    static public String stopChar;

    static {
        StringBuffer x = new StringBuffer("[\\s");
        x.append("\\!");
        x.append("\\%");
        x.append("\\&");
        x.append("\\'");
        x.append("\\(");
        x.append("\\)");
        x.append("\\*");
        x.append("\\+");
        x.append("\\,");
        x.append("\\-");
        x.append("\\.");
        x.append("\\/");
        x.append("\\:");
        x.append("\\;");
        x.append("\\=");
        x.append("\\?");
        x.append("\\[");
        x.append("\\]");
        x.append("\\^");
        x.append("\\…");
        x.append("\\`");
        x.append("\\{");
        x.append("\\|");
        x.append("\\}");
        x.append("\\~");
        x.append("]");
        stopChar = x.toString();
    }

    public String getFormattedText(String formattedText ) {

        formattedText = getFormattedTextForUserProfiles(formattedText);

        Set<HashTag> tags = this.getTags();
        formattedText = getFormattedTextForHashTags(tags,formattedText);

        Set<Media> media = this.getMedia();
        formattedText = getFormattedTextForMedia(media, formattedText);

        Set<Url> urls = this.getUrls();
        formattedText = getFormattedTextForUrls(urls, formattedText);

        Set<Mention> mentions = this.getMentions();
        formattedText = getFormattedTextForMentions(mentions, formattedText);

        Set<TickerSymbol> tickerSymbols = this.getTickerSymbols();
        formattedText = getFormattedTextForTickerSymbols(tickerSymbols, formattedText);

        return formattedText;
    }
}
