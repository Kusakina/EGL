package egl.client.service.model.topic;

import egl.client.model.core.topic.TopicTasks;
import egl.client.model.local.topic.category.Category;
import egl.client.repository.local.topic.CategoryRepository;
import egl.client.service.model.AbstractEntityService;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractEntityService<Category, CategoryRepository> {

    @Setter private TopicTasks categoryTopicTasks;

    public CategoryService(CategoryRepository repository) {
        super(repository);
    }

    @Override
    public void save(Category category) {
        if (null == category.getTopicTasks()) {
            category.setTopicTasks(categoryTopicTasks);
        }

        super.save(category);
    }
}
