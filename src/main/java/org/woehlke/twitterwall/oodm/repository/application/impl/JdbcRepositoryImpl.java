package org.woehlke.twitterwall.oodm.repository.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.repository.application.JdbcRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*Createdbytwon14.07.17.
*/
@Repository
class JdbcRepositoryImpl implements JdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    private static String[] tableNames={
        "tweet_hashtag",
        "tweet_media",
        "tweet_mention",
        "tweet_tickersymbol",
        "tweet_url",
        "userprofile_hashtag",
        "userprofile_media",
        "userprofile_mention",
        "userprofile_tickersymbol",
        "userprofile_url"
    };

    @Autowired
    public JdbcRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<String,Integer> getTableCount(){
        Map<String,Integer> result = new HashMap<>();
        for(String tableName:tableNames){
            String SQL = "select count(*) as z from "+tableName+";";
            List<Integer> resultSet = jdbcTemplate.queryForList(SQL,Integer.class);
            result.put(tableName,resultSet.get(0));
        }
        return result;
    }

    @Override
    public List<String> tableNamess(){
        List<String> myTablenames = new ArrayList<>();
        for(String tableName:tableNames){
            myTablenames.add(tableName);
        }
        return myTablenames;
    }

}
