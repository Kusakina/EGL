package egl.client.service.model.local;

import egl.client.model.local.topic.LocalTopicInfo;
import egl.client.model.local.topic.category.Category;
import egl.client.repository.local.topic.CategoryRepository;
import egl.client.service.model.core.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractEntityService<Category, CategoryRepository>
        implements SpecificLocalTopicInfoService<Category> {

    public CategoryService(CategoryRepository repository) {
        super(repository);
    }

    @Override
    public Category findBy(LocalTopicInfo localTopicInfo) {
        return repository.findByLocalTopicInfo(localTopicInfo);
    }
}
