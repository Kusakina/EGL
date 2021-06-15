package egl.client.service.model.local;

import egl.client.model.local.topic.LocalTopicInfo;
import egl.client.repository.local.topic.LocalTopicInfoRepository;
import egl.client.service.model.core.TopicInfoService;
import org.springframework.stereotype.Service;

@Service
public class LocalTopicInfoService
        extends TopicInfoService<LocalTopicInfo, LocalTopicInfoRepository> {

    public LocalTopicInfoService(LocalTopicInfoRepository repository) {
        super(repository);
    }
}
