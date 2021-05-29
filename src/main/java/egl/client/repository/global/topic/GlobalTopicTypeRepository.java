package egl.client.repository.global.topic;

import egl.client.model.core.topic.TopicType;
import egl.client.repository.EntityRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;

@Repository
@Transactional("globalTransactionManager")
@PersistenceContext(name = "globalEntityManager")
public interface GlobalTopicTypeRepository extends EntityRepository<TopicType> { }
