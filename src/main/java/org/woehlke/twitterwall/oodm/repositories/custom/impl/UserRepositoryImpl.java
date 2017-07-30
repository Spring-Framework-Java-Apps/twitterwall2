package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.transients.*;
import org.woehlke.twitterwall.oodm.entities.transients.mapper.*;
import org.woehlke.twitterwall.oodm.repositories.custom.UserRepositoryCustom;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserRepositoryImpl extends AbstractDomainRepositoryImpl<User> implements UserRepositoryCustom {

    @Override
    public Page<Object2Entity> findAllUser2HashTag(Pageable pageRequest) {
        String pagerSQL = " OFFSET "+pageRequest.getOffset()+" LIMIT "+pageRequest.getPageSize();
        String sqlCount = "select count(*) as counted from userprofile_hashtag";
        List<Long> countedList =  jdbcTemplate.query(sqlCount, new RowMapperCount());
        long total= countedList.iterator().next().longValue();
        String sql = "select * from userprofile_hashtag"+pagerSQL;
        List<Object2Entity> list =  jdbcTemplate.query(sql, new User2HashTagRowMapper());
        PageImpl<Object2Entity> resultPage = new PageImpl<>(list,pageRequest,total);
        return resultPage;
    }

    @Override
    public Page<Object2Entity> findAllUser2Media(Pageable pageRequest) {
        String pagerSQL = " OFFSET "+pageRequest.getOffset()+" LIMIT "+pageRequest.getPageSize();
        String sqlCount = "select count(*) as counted from userprofile_media";
        List<Long> countedList =  jdbcTemplate.query(sqlCount, new RowMapperCount());
        long total= countedList.iterator().next().longValue();
        String sql = "select * from userprofile_media"+pagerSQL;
        List<Object2Entity> list =  jdbcTemplate.query(sql, new User2MediaRowMapper());
        PageImpl<Object2Entity> resultPage = new PageImpl<>(list,pageRequest,total);
        return resultPage;
    }

    @Override
    public Page<Object2Entity> findAllUser2Mentiong(Pageable pageRequest) {
        String pagerSQL = " OFFSET "+pageRequest.getOffset()+" LIMIT "+pageRequest.getPageSize();
        String sqlCount = "select count(*) as counted from userprofile_mention";
        List<Long> countedList =  jdbcTemplate.query(sqlCount, new RowMapperCount());
        long total= countedList.iterator().next().longValue();
        String sql = "select * from userprofile_mention"+pagerSQL;
        List<Object2Entity> list =  jdbcTemplate.query(sql, new User2MentionRowMapper());
        PageImpl<Object2Entity> resultPage = new PageImpl<>(list,pageRequest,total);
        return resultPage;
    }

    @Override
    public Page<Object2Entity> findAllUser2Url(Pageable pageRequest) {
        String pagerSQL = " OFFSET "+pageRequest.getOffset()+" LIMIT "+pageRequest.getPageSize();
        String sqlCount = "select count(*) as counted from userprofile_url";
        List<Long> countedList =  jdbcTemplate.query(sqlCount, new RowMapperCount());
        long total= countedList.iterator().next().longValue();
        String sql = "select * from userprofile_url"+pagerSQL;
        List<Object2Entity> list =  jdbcTemplate.query(sql, new User2UrlRowMapper());
        PageImpl<Object2Entity> resultPage = new PageImpl<>(list,pageRequest,total);
        return resultPage;
    }

    @Override
    public Page<Object2Entity> findAllUser2TickerSymbol(Pageable pageRequest) {
        String pagerSQL = " OFFSET "+pageRequest.getOffset()+" LIMIT "+pageRequest.getPageSize();
        String sqlCount = "select count(*) as counted from userprofile_tickersymbol";
        List<Long> countedList =  jdbcTemplate.query(sqlCount, new RowMapperCount());
        long total= countedList.iterator().next().longValue();
        String sql = "select * from userprofile_tickersymbol"+pagerSQL;
        List<Object2Entity> list =  jdbcTemplate.query(sql, new User2TickerSymbolRowMapper());
        PageImpl<Object2Entity> resultPage = new PageImpl<>(list,pageRequest,total);
        return resultPage;
    }

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager,DataSource dataSource) {
        super(entityManager);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
