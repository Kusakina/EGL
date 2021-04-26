package egl.client.service;

import egl.client.model.topic.LocalTopic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface LocalTopicService extends CrudRepository<LocalTopic, Long> {
}
