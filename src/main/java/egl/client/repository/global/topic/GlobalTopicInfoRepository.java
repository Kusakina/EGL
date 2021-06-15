package egl.client.repository.global.topic;

import egl.client.model.global.topic.GlobalTopicInfo;
import egl.client.repository.core.topic.TopicInfoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalTopicInfoRepository extends TopicInfoRepository<GlobalTopicInfo> {

}
