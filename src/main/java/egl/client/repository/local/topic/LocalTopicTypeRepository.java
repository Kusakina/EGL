package egl.client.repository.local.topic;

import egl.client.model.core.topic.TopicType;
import egl.client.repository.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalTopicTypeRepository extends EntityRepository<TopicType> { }
