package egl.client.repository.local.topic;

import egl.client.model.local.topic.LocalTopicInfo;
import egl.client.repository.core.topic.TopicInfoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalTopicInfoRepository extends TopicInfoRepository<LocalTopicInfo> {

}
