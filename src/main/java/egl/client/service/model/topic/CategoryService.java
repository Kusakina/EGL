package egl.client.service.model.topic;

import egl.client.model.local.topic.category.Category;
import egl.client.repository.local.topic.CategoryRepository;
import egl.client.service.model.AbstractEntityService;
import egl.client.model.core.topic.TopicType;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractEntityService<Category, CategoryRepository> {

    @Setter private TopicType categoryTopicType;

    public CategoryService(CategoryRepository repository) {
        super(repository);
    }

    @Override
    public void save(Category category) {
        if (null == category.getTopicType()) {
            category.setTopicType(categoryTopicType);
        }

        super.save(category);
    }
}
