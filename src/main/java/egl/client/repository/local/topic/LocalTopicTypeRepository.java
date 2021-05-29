package egl.client.repository.local.topic;

import egl.client.model.core.topic.TopicType;
import egl.client.repository.EntityRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;

@Repository
@Transactional("localTransactionManager")
@PersistenceContext(name = "localEntityManager")
public interface LocalTopicTypeRepository extends EntityRepository<TopicType> { }
