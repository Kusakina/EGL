package egl.client.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import egl.client.model.topic.Theory;
import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Language;
import egl.client.model.topic.category.Translation;
import egl.client.model.topic.category.Word;
import egl.client.repository.CategoryRepository;
import egl.client.repository.TaskRepository;
import egl.client.repository.TestRepository;
import egl.client.repository.TheoryRepository;
import egl.client.repository.TopicTypeRepository;
import egl.client.repository.TranslationRepository;
import egl.client.repository.WordRepository;
import egl.core.model.task.Task;
import egl.core.model.task.Test;
import egl.core.model.topic.TopicType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DatabaseInitializer {

    private final TaskRepository taskRepository;
    private final TestRepository testRepository;
    private final TopicTypeRepository topicTypeRepository;
    private final TheoryRepository theoryRepository;
    private final WordRepository wordRepository;
    private final TranslationRepository translationRepository;
    private final CategoryRepository categoryRepository;

    public void run() {
        Task categoryTheoryTask = new Task(
                "Информация о категории",
                "Здесь описаны все переводы в категории",
                "task.category.CategoryTheoryController");
        taskRepository.save(categoryTheoryTask);

        String testSceneName = "task.TestController";
        String testDescription = "На каждой вкладке одно задание.\n" +
                "Чтобы задание зачли - необходимо нажать кнопку \"Завершить\" внутри вкладки с заданием.\n";
        Test categoryTest = new Test("Итоговый тест по категории", testDescription, testSceneName, new ArrayList<>());
        testRepository.save(categoryTest);

        TopicType categoryTopicType = new TopicType(
                "Категория", "Набор переводов, объединенных общей темой",
                categoryTheoryTask, new ArrayList<>(), categoryTest
        );
        topicTypeRepository.save(categoryTopicType);

        Theory rainbowColorsTheory = new Theory(
                "В радуге 7 цветов - красный, оранжевый, желтый, зеленый,\n" +
                        " голубой, синий, фиолетовый.\n" +
                        "Для запоминания цветов и порядка используется мнемоника\n" +
                        "'(К)аждый (о)хотник (ж)елает (з)нать, (г)де (с)идит (ф)азан'."
        );
        theoryRepository.save(rainbowColorsTheory);

        List<String> russianRainbowColors = Arrays.asList(
                "красный", "оранжевый", "желтый", "зеленый", "голубой", "синий", "фиолетовый"
        );

        List<String> englishRainbowColors = Arrays.asList(
                "red", "orange", "yellow", "green", "blue", "indigo", "violet"
        );

        List<Translation> rainbowColorsTranslations = new ArrayList<>();
        for (int i = 0; i < russianRainbowColors.size(); ++i) {
            Word russianWord = new Word(russianRainbowColors.get(i), Language.RUSSIAN);
            wordRepository.save(russianWord);

            Word englishWord = new Word(englishRainbowColors.get(i), Language.ENGLISH);
            wordRepository.save(englishWord);

            Translation translation = new Translation(russianWord, englishWord);
            translationRepository.save(translation);

            rainbowColorsTranslations.add(translation);
        }

        Category rainbowColorsTopic = new Category(
                "Цвета радуги", "7 цветов радуги",
                categoryTopicType, rainbowColorsTheory, rainbowColorsTranslations
        );

        categoryRepository.save(rainbowColorsTopic);
    }
}
