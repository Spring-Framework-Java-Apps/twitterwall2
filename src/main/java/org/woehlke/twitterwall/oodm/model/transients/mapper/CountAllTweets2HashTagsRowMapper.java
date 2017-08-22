package org.woehlke.twitterwall.oodm.model.transients.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.woehlke.twitterwall.oodm.model.transients.HashTagCounted;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountAllTweets2HashTagsRowMapper implements RowMapper<HashTagCounted> {

    /**
     * @see org.woehlke.twitterwall.oodm.model.HashTag
     */
    public final static String SQL_COUNT_ALL_TWEET_2_HASHTAG = "select count(tweet_id) as number_of_tweets,hash_tags_id,text from tweet_hashtag,hashtag where hashtag.id = hash_tags_id group by hash_tags_id,hashtag.id order by number_of_tweets desc";

    @Override
    public HashTagCounted mapRow(ResultSet resultSet, int i) throws SQLException {

        long id = resultSet.getLong("hash_tags_id");
        long number = resultSet.getLong("number_of_tweets");
        String text = resultSet.getString("text");

        return new HashTagCounted(id, number, text);
    }
}
