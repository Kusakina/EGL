package egl.client.repository.global.topic;

import egl.client.model.core.topic.Topic;
import egl.client.model.global.topic.GlobalTopicInfo;
import egl.client.repository.core.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalTopicInfoRepository extends EntityRepository<GlobalTopicInfo> {

    GlobalTopicInfo findByTopicAndLocalHashCode(Topic topic, int localHashCode);
}
