package egl.client.repository.topic.category;

import egl.client.model.topic.category.Category;
import egl.client.repository.DatabaseDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends DatabaseDataRepository<Category> {  }
