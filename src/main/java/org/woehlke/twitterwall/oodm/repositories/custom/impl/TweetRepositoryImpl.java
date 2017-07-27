package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.transients.*;
import org.woehlke.twitterwall.oodm.entities.transients.mapper.*;
import org.woehlke.twitterwall.oodm.repositories.custom.TweetRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class TweetRepositoryImpl implements TweetRepositoryCustom {

    private final EntityManager entityManager;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TweetRepositoryImpl(EntityManager entityManager,DataSource dataSource) {
        this.entityManager = entityManager;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Tweet findByUniqueId(Tweet domainObject) {
        String name="Tweet.findByUniqueId";
        TypedQuery<Tweet> query = entityManager.createNamedQuery(name,Tweet.class);
        query.setParameter("idTwitter",domainObject.getIdTwitter());
        List<Tweet> resultList = query.getResultList();
        if(resultList.size()>0){
            return resultList.iterator().next();
        } else {
            return null;
        }
    }

    @Override
    public Page<Object2Entity> findAllTweet2HashTag(Pageable pageRequest) {
        String pagerSQL = " OFFSET "+pageRequest.getOffset()+" LIMIT "+pageRequest.getPageSize();
        String sqlCount = "select count(*) as counted from tweet_hashtag";
        List<Long> countedList =  jdbcTemplate.query(sqlCount, new RowMapperCount());
        long total= countedList.iterator().next().longValue();
        String sql = "select * from tweet_hashtag"+pagerSQL;
        List<Object2Entity> list =  jdbcTemplate.query(sql, new Tweet2HashTagRowMapper());
        PageImpl<Object2Entity> resultPage = new PageImpl<>(list,pageRequest,total);
        return resultPage;
    }

    @Override
    public Page<Object2Entity> findAllTweet2Media(Pageable pageRequest) {
        String pagerSQL = " OFFSET "+pageRequest.getOffset()+" LIMIT "+pageRequest.getPageSize();
        String sqlCount = "select count(*) as counted from tweet_media";
        List<Long> countedList =  jdbcTemplate.query(sqlCount, new RowMapperCount());
        long total= countedList.iterator().next().longValue();
        String sql = "select * from tweet_media"+pagerSQL;
        List<Object2Entity> list =  jdbcTemplate.query(sql, new Tweet2MediaRowMapper());
        PageImpl<Object2Entity> resultPage = new PageImpl<>(list,pageRequest,total);
        return resultPage;
    }

    @Override
    public Page<Object2Entity> findAllTweet2Mention(Pageable pageRequest) {
        String pagerSQL = " OFFSET "+pageRequest.getOffset()+" LIMIT "+pageRequest.getPageSize();
        String sqlCount = "select count(*) as counted from tweet_mention";
        List<Long> countedList =  jdbcTemplate.query(sqlCount, new RowMapperCount());
        long total= countedList.iterator().next().longValue();
        String sql = "select * from tweet_mention"+pagerSQL;
        List<Object2Entity> list =  jdbcTemplate.query(sql, new Tweet2MentionRowMapper());
        PageImpl<Object2Entity> resultPage = new PageImpl<>(list,pageRequest,total);
        return resultPage;
    }

    @Override
    public Page<Object2Entity> findAllTweet2Url(Pageable pageRequest) {
        String pagerSQL = " OFFSET "+pageRequest.getOffset()+" LIMIT "+pageRequest.getPageSize();
        String sqlCount = "select count(*) as counted from tweet_url";
        List<Long> countedList =  jdbcTemplate.query(sqlCount, new RowMapperCount());
        long total= countedList.iterator().next().longValue();
        String sql = "select * from tweet_url"+pagerSQL;
        List<Object2Entity> list =  jdbcTemplate.query(sql, new Tweet2UrlRowMapper());
        PageImpl<Object2Entity> resultPage = new PageImpl<>(list,pageRequest,total);
        return resultPage;
    }

    @Override
    public Page<Object2Entity> findAllTweet2TickerSymbol(Pageable pageRequest){
        String pagerSQL = " OFFSET "+pageRequest.getOffset()+" LIMIT "+pageRequest.getPageSize();
        String sqlCount = "select count(*) as counted from tweet_tickersymbol";
        List<Long> countedList =  jdbcTemplate.query(sqlCount, new RowMapperCount());
        long total= countedList.iterator().next().longValue();
        String sql = "select * from tweet_tickersymbol"+pagerSQL;
        List<Object2Entity> list =  jdbcTemplate.query(sql, new Tweet2TickerSymbolRowMapper());
        PageImpl<Object2Entity> resultPage = new PageImpl<>(list,pageRequest,total);
        return resultPage;
    }
}
