package egl.client.service.model.core;

import java.util.Optional;

import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicInfo;
import egl.client.repository.core.topic.TopicInfoRepository;
import egl.client.service.model.EntityServiceException;

public abstract class TopicInfoService<
        T extends TopicInfo,
        RepositoryType extends TopicInfoRepository<T>>
        extends AbstractEntityService<T, RepositoryType> {

    public TopicInfoService(RepositoryType repository) {
        super(repository);
    }

    public T findBy(Topic topic) {
        try {
            return repository.findByTopic(topic);
        } catch (Exception e) {
            throw new EntityServiceException();
        }
    }

    public Optional<T> findBy(long hashCode) {
        try {
            return repository.findByTopicHashCode(hashCode);
        } catch (Exception e) {
            throw new EntityServiceException();
        }
    }
}
