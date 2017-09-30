package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.model.HashTag;
import org.woehlke.twitterwall.oodm.model.transients.HashTagCounted;
import org.woehlke.twitterwall.oodm.model.transients.mapper.CountAllTweets2HashTagsRowMapper;
import org.woehlke.twitterwall.oodm.model.transients.mapper.CountAllUsers2HashTagsRowMapper;
import org.woehlke.twitterwall.oodm.model.transients.mapper.RowMapperCount;
import org.woehlke.twitterwall.oodm.repositories.custom.HashTagRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.util.List;

import static org.woehlke.twitterwall.oodm.model.transients.mapper.CountAllTweets2HashTagsRowMapper.SQL_COUNT_ALL_TWEET_2_HASHTAG;
import static org.woehlke.twitterwall.oodm.model.transients.mapper.CountAllUsers2HashTagsRowMapper.SQL_COUNT_ALL_USER_2_HASHTAG;

@Repository
public class HashTagRepositoryImpl implements HashTagRepositoryCustom {

    private final EntityManager entityManager;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HashTagRepositoryImpl(EntityManager entityManager, DataSource dataSource) {
        this.entityManager = entityManager;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public HashTag findByUniqueId(HashTag domainObject) {
        String name="HashTag.findByUniqueId";
        TypedQuery<HashTag> query = entityManager.createNamedQuery(name,HashTag.class);
        query.setParameter("text",domainObject.getText());
        List<HashTag> resultList = query.getResultList();
        if(resultList.size()>0){
            return resultList.iterator().next();
        } else {
            return null;
        }
    }

    /**
     * @see org.woehlke.twitterwall.oodm.model.transients.mapper.CountAllUsers2HashTagsRowMapper#SQL_COUNT_ALL_USER_2_HASHTAG
     */
    @Override
    public Page<HashTagCounted> countAllUser2HashTag(Pageable pageRequest) {
        String pagerSQL = " OFFSET "+pageRequest.getOffset()+" LIMIT "+pageRequest.getPageSize();
        String sqlCount = "select count(*) as counted from ("+SQL_COUNT_ALL_USER_2_HASHTAG+") as foo";
        List<Long> countedList =  jdbcTemplate.query(sqlCount, new RowMapperCount());
        long total= countedList.iterator().next().longValue();
        String sql = SQL_COUNT_ALL_USER_2_HASHTAG+pagerSQL;
        List<HashTagCounted> list =  jdbcTemplate.query(sql, new CountAllUsers2HashTagsRowMapper());
        PageImpl<HashTagCounted> resultPage = new PageImpl<>(list,pageRequest,total);
        return resultPage;
    }

    /**
     * @see org.woehlke.twitterwall.oodm.model.transients.mapper.CountAllTweets2HashTagsRowMapper#SQL_COUNT_ALL_TWEET_2_HASHTAG
     */
    @Override
    public Page<HashTagCounted> countAllTweet2HashTag(Pageable pageRequest) {
        String pagerSQL = " OFFSET "+pageRequest.getOffset()+" LIMIT "+pageRequest.getPageSize();
        String sqlCount = "select count(*) as counted from ("+SQL_COUNT_ALL_TWEET_2_HASHTAG+") as foo";
        List<Long> countedList =  jdbcTemplate.query(sqlCount, new RowMapperCount());
        long total= countedList.iterator().next().longValue();
        String sql = SQL_COUNT_ALL_TWEET_2_HASHTAG+pagerSQL;
        List<HashTagCounted> list =  jdbcTemplate.query(sql, new CountAllTweets2HashTagsRowMapper());
        PageImpl<HashTagCounted> resultPage = new PageImpl<>(list,pageRequest,total);
        return resultPage;
    }
}
