package egl.client.repository.local.topic;

import egl.client.model.local.topic.LocalTopicInfo;
import egl.client.model.local.topic.category.Category;
import egl.client.repository.core.EntityRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends EntityRepository<Category>,
        JpaRepository<Category, Long> {

    Category findByLocalTopicInfo(LocalTopicInfo localTopicInfo);
}
