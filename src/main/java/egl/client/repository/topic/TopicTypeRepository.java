package egl.client.repository.topic;

import egl.client.repository.DatabaseEntityRepository;
import egl.core.model.topic.TopicType;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicTypeRepository extends DatabaseEntityRepository<TopicType> { }
