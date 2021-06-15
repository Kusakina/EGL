package egl.client.service.model.core;

import egl.client.model.core.topic.Topic;
import egl.client.repository.core.topic.TopicRepository;

public abstract class TopicService<RepositoryType extends TopicRepository>
        extends AbstractEntityService<Topic, RepositoryType>
        implements TopicByLocalService  {

    protected final TopicInfoService<?, ?> localTopicInfoService;

    public TopicService(RepositoryType topicRepository,
                        TopicInfoService<?, ?> localTopicInfoService) {
        super(topicRepository);
        this.localTopicInfoService = localTopicInfoService;
    }

    @Override
    public long findHashByLocal(Topic localTopic) {
        return localTopicInfoService.findBy(localTopic).getTopicHashCode();
    }
}
