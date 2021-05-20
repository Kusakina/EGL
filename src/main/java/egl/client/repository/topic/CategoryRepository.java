package egl.client.repository.topic;

import egl.client.model.topic.category.Category;
import egl.client.repository.DatabaseEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends DatabaseEntityRepository<Category> {  }