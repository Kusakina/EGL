package egl.client.service;

import egl.client.repository.TaskRepository;
import egl.core.model.task.Task;
import egl.core.model.task.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseInitializer {

    private final TaskRepository taskRepository;

    public void run() {
        Task categoryTheoryTask = new Task("Информация о категории", "Здесь описаны все переводы в категории", "CategoryTheoryController");
        taskRepository.save(categoryTheoryTask);
    }
}
