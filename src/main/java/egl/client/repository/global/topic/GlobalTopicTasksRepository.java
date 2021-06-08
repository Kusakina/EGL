package egl.client.repository.global.topic;

import javax.persistence.PersistenceContext;

import egl.client.model.core.topic.TopicTasks;
import egl.client.repository.core.EntityRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional("globalTransactionManager")
@PersistenceContext(name = "globalEntityManager")
public interface GlobalTopicTasksRepository extends EntityRepository<TopicTasks> { }
