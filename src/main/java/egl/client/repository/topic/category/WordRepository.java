package egl.client.repository.topic.category;

import egl.client.model.topic.category.Word;
import egl.client.repository.DatabaseEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends DatabaseEntityRepository<Word> { }
