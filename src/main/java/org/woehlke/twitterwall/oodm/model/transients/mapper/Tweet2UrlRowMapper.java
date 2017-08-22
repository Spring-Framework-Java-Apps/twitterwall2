package org.woehlke.twitterwall.oodm.model.transients.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.woehlke.twitterwall.oodm.model.transients.Object2Entity;
import org.woehlke.twitterwall.oodm.model.transients.Object2EntityTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Tweet2UrlRowMapper implements RowMapper<Object2Entity> {

    @Override
    public Object2Entity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Object2Entity(
                rs.getLong("tweet_id"),
                rs.getLong("urls_id"),
                Object2EntityTable.TWEET_URL
        );
    }
}
