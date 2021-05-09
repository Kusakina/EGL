package egl.client.service.model;

import egl.client.model.topic.category.Category;
import egl.client.repository.topic.category.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractDescribedDataService<Category, CategoryRepository> {

    public CategoryService(CategoryRepository repository) {
        super(repository);
    }
}
