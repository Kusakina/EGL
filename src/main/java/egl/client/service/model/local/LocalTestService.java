package egl.client.service.model.local;

import egl.client.model.core.task.Task;
import egl.client.model.core.task.Test;
import egl.client.repository.local.task.TestRepository;
import egl.client.service.model.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class LocalTestService extends AbstractEntityService<Test, TestRepository> {

    public LocalTestService(TestRepository repository) {
        super(repository);
    }

    public Test findBy(Task task) {
        return repository.findByTask(task);
    }
}
