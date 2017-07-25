package org.woehlke.twitterwall.oodm.entities.transients.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.woehlke.twitterwall.oodm.entities.transients.Object2Entity;
import org.woehlke.twitterwall.oodm.entities.transients.Object2EntityTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Tweet2MediaRowMapper implements RowMapper<Object2Entity> {

    @Override
    public Object2Entity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Object2Entity(
                rs.getLong("tweet_id"),
                rs.getLong(" tweet_media"),
                Object2EntityTable.TWEET_MEDIA
        );
    }
}
