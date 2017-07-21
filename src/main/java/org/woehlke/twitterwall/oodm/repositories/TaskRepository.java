package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Task;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task,Long> {
}
