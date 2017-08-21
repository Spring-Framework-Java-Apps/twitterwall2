package org.woehlke.twitterwall.oodm.model.transients.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.woehlke.twitterwall.oodm.model.transients.Object2Entity;
import org.woehlke.twitterwall.oodm.model.transients.Object2EntityTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserList2MemberRowMapper implements RowMapper<Object2Entity> {

    @Override
    public Object2Entity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Object2Entity(
                rs.getLong("user_list_id"),
                rs.getLong("members_id"),
                Object2EntityTable.USERLIST_MEMBERS
        );
    }
}
