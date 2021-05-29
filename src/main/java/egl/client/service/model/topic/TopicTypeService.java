package egl.client.service.model.topic;

import egl.client.repository.core.topic.TopicTypeRepository;
import egl.client.service.model.AbstractEntityService;
import egl.client.model.core.topic.TopicType;
import org.springframework.stereotype.Service;

@Service
public class TopicTypeService extends AbstractEntityService<TopicType, TopicTypeRepository> {

    public TopicTypeService(TopicTypeRepository repository) {
        super(repository);
    }
}
