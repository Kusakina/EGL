package egl.client.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import egl.client.model.profile.LocalProfile;
import egl.client.model.topic.Theory;
import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Language;
import egl.client.model.topic.category.Translation;
import egl.client.model.topic.category.Word;
import egl.client.repository.profile.LocalProfileRepository;
import egl.client.repository.task.TaskRepository;
import egl.client.repository.task.TestRepository;
import egl.client.repository.topic.TheoryRepository;
import egl.client.repository.topic.TopicTypeRepository;
import egl.client.repository.topic.category.CategoryRepository;
import egl.client.repository.topic.category.TranslationRepository;
import egl.client.repository.topic.category.WordRepository;
import egl.core.model.task.Task;
import egl.core.model.task.Test;
import egl.core.model.topic.TopicType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DatabaseInitializationService {

    private final TaskRepository taskRepository;
    private final TestRepository testRepository;
    private final TopicTypeRepository topicTypeRepository;
    private final TheoryRepository theoryRepository;
    private final WordRepository wordRepository;
    private final TranslationRepository translationRepository;
    private final CategoryRepository categoryRepository;
    private final LocalProfileRepository localProfileRepository;

    public void run() {
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

        /*Task TestTable = new Task(
                "Задание 4",
                "Необходимо заполнить таблицу",
                "task.TestControllerTask4");
        taskRepository.save(TestTable);*/
        String testSceneName = "task.TestController";
        String testDescription = "На каждой вкладке одно задание.\n" +
                "Чтобы задание зачли - необходимо нажать кнопку \"Завершить\" внутри вкладки с заданием.\n";

        List<Task> categoryTestTasks = Arrays.asList(oneOfFourTask, matchIndexTask, trueFalseTask);
        Test categoryTest = new Test("Итоговый тест по категории", testDescription, testSceneName, categoryTestTasks);

        List<Task> categoryTasks = Arrays.asList(missingLettersTask);
        TopicType categoryTopicType = new TopicType(
                "Категория", "Набор переводов, объединенных общей темой",
                categoryTheoryTask, categoryTasks, categoryTest
        );
        topicTypeRepository.save(categoryTopicType);

        Theory rainbowColorsTheory = new Theory(
                "В радуге 7 цветов - красный, оранжевый, желтый, зеленый,\n" +
                        " голубой, синий, фиолетовый.\n" +
                        "Для запоминания цветов и порядка используется мнемоника\n" +
                        "'(К)аждый (о)хотник (ж)елает (з)нать, (г)де (с)идит (ф)азан'.\n"+
                        "'(R)ichard (o)f (Y)ork (g)ave (b)attle (i)n (v)ain'."

        );

        List<String> russianRainbowColors = Arrays.asList(
                "красный", "оранжевый", "желтый", "зеленый", "голубой", "синий", "фиолетовый"
        );

        List<String> englishRainbowColors = Arrays.asList(
                "red", "orange", "yellow", "green", "blue", "indigo", "violet"
        );

        List<Translation> rainbowColorsTranslations = new ArrayList<>();
        for (int i = 0; i < russianRainbowColors.size(); ++i) {
            Word russianWord = new Word(russianRainbowColors.get(i), Language.RUSSIAN);
            Word englishWord = new Word(englishRainbowColors.get(i), Language.ENGLISH);
            Translation translation = new Translation(russianWord, englishWord);

            rainbowColorsTranslations.add(translation);
        }

        Category rainbowColorsTopic = new Category(
                "Цвета радуги", "7 цветов радуги",
                categoryTopicType, rainbowColorsTheory, rainbowColorsTranslations
        );

        categoryRepository.save(rainbowColorsTopic);

        LocalProfile vasyaPupkinLocalProfile = new LocalProfile("Вася Пупкин", "Едет в Магадан");
        localProfileRepository.save(vasyaPupkinLocalProfile);
    }
}
