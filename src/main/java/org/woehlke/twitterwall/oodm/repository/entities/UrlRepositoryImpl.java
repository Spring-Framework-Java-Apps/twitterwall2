package org.woehlke.twitterwall.oodm.repository.entities;

import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.entities.entities.Url;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class UrlRepositoryImpl implements UrlRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Url persist(Url url) {
        entityManager.persist(url);
        return url;
    }

    @Override
    public Url update(Url url) {
        return entityManager.merge(url);
    }

    @Override
    public Url findByDisplayExpandedUrl(String display, String expanded, String url) {
        try {
            String SQL = "select t from Url as t where t.display=:display and t.expanded=:expanded and t.url=:url";
            TypedQuery<Url> query = entityManager.createQuery(SQL,Url.class);
            query.setParameter("display",display);
            query.setParameter("expanded",expanded);
            query.setParameter("url",url);
            Url result = query.getSingleResult();
            return result;
        } catch (NoResultException e){
            return null;
        }
    }
}
