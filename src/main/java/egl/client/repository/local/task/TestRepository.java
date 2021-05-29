package egl.client.repository.local.task;

import egl.client.model.core.task.Task;
import egl.client.model.local.task.Test;
import egl.client.repository.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalTestRepository extends EntityRepository<Test> {

    Test findByTask(Task task);
}
