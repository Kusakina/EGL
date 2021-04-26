package egl.client.repository;

import egl.client.model.topic.LocalTopic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalTopicRepository extends CrudRepository<LocalTopic, Long> { }
