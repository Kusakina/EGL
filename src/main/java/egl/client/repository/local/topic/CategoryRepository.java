package egl.client.repository.local.topic;

import egl.client.model.local.topic.LocalTopicInfo;
import egl.client.model.local.topic.category.Category;
import egl.client.repository.core.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends EntityRepository<Category> {

    Category findByLocalTopicInfo(LocalTopicInfo localTopicInfo);
}
