package egl.client.repository.global.topic;

import egl.client.model.core.topic.Topic;
import egl.client.repository.core.task.TopicRepository;
import egl.client.repository.global.GlobalEntityManagerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GlobalTopicRepository
        extends GlobalEntityManagerRepository<Topic>
        implements TopicRepository {

    @Override
    protected Class<Topic> getEntityClass() {
        return Topic.class;
    }
}
