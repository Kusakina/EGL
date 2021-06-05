package egl.client.repository.local.topic;

import egl.client.model.core.topic.Topic;
import egl.client.model.local.topic.LocalTopicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalTopicInfoRepository extends JpaRepository<LocalTopicInfo, Long> {

    LocalTopicInfo findByTopic(Topic topic);
}
