package egl.client.repository;

import egl.core.model.task.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends DatabaseDataRepository<Task> { }
