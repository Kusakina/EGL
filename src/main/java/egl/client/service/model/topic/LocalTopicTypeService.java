package egl.client.service.model.topic;

import egl.client.repository.local.topic.LocalTopicTypeRepository;
import egl.client.service.model.AbstractEntityService;
import egl.client.model.core.topic.TopicType;
import org.springframework.stereotype.Service;

@Service
public class LocalTopicTypeService extends AbstractEntityService<TopicType, LocalTopicTypeRepository> {

    public LocalTopicTypeService(LocalTopicTypeRepository repository) {
        super(repository);
    }
}
