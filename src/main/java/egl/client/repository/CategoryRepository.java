package egl.client.repository;

import java.util.List;

import egl.client.model.topic.category.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends DatabaseDataRepository<Category> {  }
