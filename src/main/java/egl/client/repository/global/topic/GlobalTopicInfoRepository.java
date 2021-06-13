package egl.client.repository.global.topic;

import java.util.Optional;

import egl.client.model.core.topic.Topic;
import egl.client.model.global.topic.GlobalTopicInfo;
import egl.client.repository.global.GlobalEntityManagerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GlobalTopicInfoRepository
        extends GlobalEntityManagerRepository<GlobalTopicInfo> {

    @Override
    protected Class<GlobalTopicInfo> getEntityClass() {
        return GlobalTopicInfo.class;
    }

    public Optional<GlobalTopicInfo> findByTopic(Topic topic) {
        return findByField("topic", topic);
    }
}
