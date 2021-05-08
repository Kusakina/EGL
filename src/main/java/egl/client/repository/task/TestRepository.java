package egl.client.repository.task;

import egl.client.repository.DatabaseDataRepository;
import egl.core.model.task.Test;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends DatabaseDataRepository<Test> { }
