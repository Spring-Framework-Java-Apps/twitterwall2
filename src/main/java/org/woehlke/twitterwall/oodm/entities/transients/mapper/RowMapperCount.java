package org.woehlke.twitterwall.oodm.entities.transients.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RowMapperCount implements RowMapper<Long> {

    @Override
    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getLong("counted");
    }
}
