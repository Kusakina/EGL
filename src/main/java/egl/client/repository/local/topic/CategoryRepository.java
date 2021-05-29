package egl.client.repository.local.topic;

import egl.client.model.local.topic.category.Category;
import egl.client.repository.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends EntityRepository<Category> {  }
