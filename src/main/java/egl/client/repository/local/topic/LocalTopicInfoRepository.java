package egl.client.repository.local.topic;

import egl.client.model.core.topic.Topic;
import egl.client.model.local.topic.LocalTopicInfo;
import egl.client.repository.core.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalTopicInfoRepository extends EntityRepository<LocalTopicInfo> {

    LocalTopicInfo findByTopic(Topic topic);
}
