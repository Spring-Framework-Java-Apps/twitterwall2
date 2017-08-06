package org.woehlke.twitterwall.oodm.entities.transients.mapper;


import org.springframework.jdbc.core.RowMapper;
import org.woehlke.twitterwall.oodm.entities.transients.HashTagCounted;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountAllUsers2HashTagsRowMapper implements RowMapper<HashTagCounted> {

    public final static String SQL_COUNT_ALL_USER_2_HASHTAG = "select count(user_id) as number_of_users,hash_tags_id,text from userprofile_hashtag,hashtag where hashtag.id = hash_tags_id group by hash_tags_id,hashtag.id order by number_of_users desc";

    @Override
    public HashTagCounted mapRow(ResultSet resultSet, int i) throws SQLException {

        long id = resultSet.getLong("hash_tags_id");
        long number = resultSet.getLong("number_of_users");
        String text = resultSet.getString("text");

        return new HashTagCounted(id, number, text);
    }
}
