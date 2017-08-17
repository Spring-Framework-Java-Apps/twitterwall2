package org.woehlke.twitterwall.oodm.service;

public interface DomainObjectMinimalServiceTest {

    void findById() throws Exception;

    void getAll() throws Exception;

    void count() throws Exception;

    void findByUniqueId() throws Exception;

    void areDependenciesLoaded() throws Exception;

    void fetchTestData() throws Exception;
}
