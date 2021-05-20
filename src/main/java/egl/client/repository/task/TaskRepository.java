package egl.client.repository.task;

import egl.client.repository.DatabaseEntityRepository;
import egl.core.model.task.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends DatabaseEntityRepository<Task> { }
