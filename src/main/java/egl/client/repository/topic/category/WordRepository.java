package egl.client.repository.topic.category;

import egl.client.model.topic.category.Word;
import egl.client.repository.DatabaseDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends DatabaseDataRepository<Word> { }
