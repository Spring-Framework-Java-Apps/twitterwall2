package org.woehlke.twitterwall.oodm.entities.transients.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.woehlke.twitterwall.oodm.entities.transients.Object2Entity;
import org.woehlke.twitterwall.oodm.entities.transients.Object2EntityTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User2MentionRowMapper implements RowMapper<Object2Entity> {

    @Override
    public Object2Entity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Object2Entity(
                rs.getLong("user_id"),
                rs.getLong("mentions_id"),
                Object2EntityTable.USERPROFILE_MENTION
        );
    }
}
