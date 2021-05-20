package egl.client.repository.task;

import egl.client.repository.DatabaseEntityRepository;
import egl.core.model.task.Test;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends DatabaseEntityRepository<Test> { }
