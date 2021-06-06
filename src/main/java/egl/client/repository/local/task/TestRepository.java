package egl.client.repository.local.task;

import egl.client.model.core.task.Task;
import egl.client.model.core.task.Test;
import egl.client.repository.core.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends EntityRepository<Test> {

    Test findByTask(Task task);
}
