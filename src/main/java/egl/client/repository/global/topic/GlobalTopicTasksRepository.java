package egl.client.repository.global.topic;

import egl.client.model.core.topic.TopicTasks;
import egl.client.repository.EntityRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;

@Repository
@Transactional("globalTransactionManager")
@PersistenceContext(name = "globalEntityManager")
public interface GlobalTopicTasksRepository extends EntityRepository<TopicTasks> { }
