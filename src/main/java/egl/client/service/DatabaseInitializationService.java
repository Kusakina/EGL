package egl.client.service;

import java.util.Arrays;
import java.util.List;

import egl.client.model.core.task.Task;
import egl.client.model.core.task.Test;
import egl.client.model.core.topic.TopicTasks;
import egl.client.model.core.topic.TopicType;
import egl.client.model.local.topic.MigrationInfo;
import egl.client.repository.local.MigrationRepository;
import egl.client.service.model.local.LocalTopicTasksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DatabaseInitializationService {

    private final MigrationRepository migrationRepository;
    private final LocalTopicTasksService localTopicTasksService;

    @Transactional("localTransactionManager")
    public void run() {
        var migrations = migrationRepository.findAll();
        if (migrations.isEmpty()) {
            migrationRepository.save(new MigrationInfo());

            Task categoryTheoryTask = new Task(
                    "Информация о категории",
                    "Здесь описаны все переводы в категории",
                    "task.category.CategoryTheoryTaskController");

            Task missingLettersTask = new Task(
                    "Буквоед",
                    "Вставьте правильные буквы на места пропусков так, чтобы полученное слово соответсвовало переводу",
                    "task.category.MissingLettersTaskController");

            Task oneOfFourTask = new Task(
                    "1 верный ответ из 4",
                    "Выберите из 4 вариантов ответов верный",
                    "task.category.OneOfFourTaskController");

            Task matchIndexTask = new Task(
                    "Сопоставь переводы",
                    "Соедините слова из левой и правой колонок",
                    "task.category.MatchIndexTaskController");

            Task trueFalseTask = new Task(
                    "Верно / неверно",
                    "Необходимо указать, соответствует ли слово своему переводу",
                    "task.category.TrueFalseTaskController");

            Task sourceOrTargetTask = new Task(
                    "Один из двух",
                    "Необходимо заполнить пропуски нужным переводом",
                    "task.category.TranslateWordController");
            String testSceneName = "task.TestController";
            String testDescription = "На каждой вкладке одно задание.\n" +
                    "Чтобы задание зачли - необходимо нажать кнопку \"Завершить\" внутри вкладки с заданием.\n";

            Task categoryTestTask = new Task("Итоговый тест по категории", testDescription, testSceneName);
            List<Task> categoryTestInnerTasks = Arrays.asList(oneOfFourTask, matchIndexTask, trueFalseTask, sourceOrTargetTask);
            Test categoryTest = new Test(categoryTestTask, categoryTestInnerTasks);

            List<Task> categoryTasks = Arrays.asList(missingLettersTask, oneOfFourTask, sourceOrTargetTask);
            TopicTasks categoryTopicTasks = new TopicTasks(
                    "Категория", "Набор переводов, объединенных общей темой",
                    TopicType.CATEGORY, categoryTheoryTask, categoryTasks, categoryTest
            );
            localTopicTasksService.save(categoryTopicTasks);
        }
    }
}
