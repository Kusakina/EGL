package egl.client.service.model.topic;

import egl.client.repository.topic.TopicTypeRepository;
import egl.client.service.model.AbstractEntityService;
import egl.core.model.topic.TopicType;
import org.springframework.stereotype.Service;

@Service
public class TopicTypeService extends AbstractEntityService<TopicType, TopicTypeRepository> {

    public TopicTypeService(TopicTypeRepository repository) {
        super(repository);
    }
}
