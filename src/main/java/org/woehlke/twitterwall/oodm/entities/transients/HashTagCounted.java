package org.woehlke.twitterwall.oodm.entities.transients;

import org.springframework.data.domain.Pageable;

import java.io.Serializable;

/**
 * Created by tw on 14.06.17.
 *
 * @author Thomas Woehlke <thomas@woehlke.org>
 *
 * @see org.woehlke.twitterwall.oodm.entities.HashTag
 * @see org.woehlke.twitterwall.oodm.entities.parts.Entities
 * @see org.woehlke.twitterwall.oodm.entities.transients.mapper.CountAllTweets2HashTagsRowMapper#SQL_COUNT_ALL_TWEET_2_HASHTAG
 * @see org.woehlke.twitterwall.oodm.entities.transients.mapper.CountAllUsers2HashTagsRowMapper#SQL_COUNT_ALL_USER_2_HASHTAG
 * @see org.woehlke.twitterwall.oodm.entities.transients.HashTagOverviewPaged
 * @see org.woehlke.twitterwall.oodm.repositories.custom.impl.HashTagRepositoryImpl#countAllTweet2HashTag(Pageable)
 * @see org.woehlke.twitterwall.oodm.repositories.custom.impl.HashTagRepositoryImpl#countAllUser2HashTag(Pageable)
 * @see org.woehlke.twitterwall.oodm.service.impl.HashTagServiceImpl#getHashTagOverview(Pageable, Pageable)
 */
public class HashTagCounted implements Serializable, Comparable<HashTagCounted> {

    private long id;
    private long number;
    private String text;
    private int lfdNr;

    public HashTagCounted(long id, long number, String text, int lfdNr) {
        this.id = id;
        this.number = number;
        this.text = text;
        this.lfdNr = lfdNr;
    }

    public HashTagCounted(long id, long number, String text) {
        this.id = id;
        this.number = number;
        this.text = text;
    }

    private HashTagCounted() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLfdNr() {
        return lfdNr;
    }

    public void setLfdNr(int lfdNr) {
        this.lfdNr = lfdNr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashTagCounted)) return false;

        HashTagCounted that = (HashTagCounted) o;

        if (id != that.id) return false;
        if (number != that.number) return false;
        if (lfdNr != that.lfdNr) return false;
        return text != null ? text.equals(that.text) : that.text == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (number ^ (number >>> 32));
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + lfdNr;
        return result;
    }

    @Override
    public String toString() {
        return "HashTagCounted{" +
                "id=" + id +
                ", number=" + number +
                ", text='" + text + '\'' +
                ", lfdNr=" + lfdNr +
                '}';
    }

    @Override
    public int compareTo(HashTagCounted o) {
        return  Long.valueOf(id).compareTo(Long.valueOf(o.getId()));
    }
}
