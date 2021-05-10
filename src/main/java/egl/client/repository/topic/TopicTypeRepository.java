package egl.client.repository.topic;

import egl.client.repository.DatabaseDataRepository;
import egl.core.model.topic.TopicType;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicTypeRepository extends DatabaseDataRepository<TopicType> { }
