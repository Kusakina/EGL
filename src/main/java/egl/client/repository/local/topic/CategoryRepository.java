package egl.client.repository.local.topic;

import egl.client.model.local.topic.LocalTopicInfo;
import egl.client.model.local.topic.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByLocalTopicInfo(LocalTopicInfo localTopicInfo);
}
