package egl.client.service.model.topic;

import egl.client.model.topic.category.Category;
import egl.client.repository.topic.category.CategoryRepository;
import egl.client.service.model.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractEntityService<Category, CategoryRepository> {

    public CategoryService(CategoryRepository repository) {
        super(repository);
    }
}
