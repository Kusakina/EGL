package egl.client.service;

import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicType;
import egl.client.model.local.topic.LocalTopicInfo;
import egl.client.model.local.topic.Theory;
import egl.client.model.local.topic.category.Category;
import egl.client.model.local.topic.category.Language;
import egl.client.model.local.topic.category.Translation;
import egl.client.model.local.topic.category.Word;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    public static void save(Category category, File file) {
        var localTopicInfo = category.getLocalTopicInfo();
        var topic = localTopicInfo.getTopic();

        try (PrintWriter out = new PrintWriter(file, StandardCharsets.UTF_8)) {
            out.println(topic.getName());
            out.println(topic.getDescription().replaceAll("\n", "; "));
            out.println(localTopicInfo.getTheory().getText().replaceAll("\n", "; "));
            for (Translation translation : category.getTranslations()) {
                var source = translation.getSource();
                var target = translation.getTarget();

                out.println(source.getText() + "\t" + target.getText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Category loadCategory(File file) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String name = in.readLine();
            String description = in.readLine().replace("; ", "\n");
            var topic = new Topic(name, description, TopicType.CATEGORY);

            String theoryText = in.readLine().replace("; ", "\n");
            var localTopicInfo = new LocalTopicInfo(topic, new Theory(theoryText));

            List<Translation> translations = new ArrayList<>();
            while (true) {
                var line = in.readLine();
                if (null == line) break;
                if (line.isBlank()) continue;

                String[] sourceTarget = line.split("\t");
                for (int i = 0; i < 2; ++i) {
                    sourceTarget[i] = sourceTarget[i].strip();
                }
                var target = new Word(sourceTarget[0], Language.of(sourceTarget[0]));
                var source = new Word(sourceTarget[1], Language.of(sourceTarget[1]));

                translations.add(new Translation(source, target));
            }

            return new Category(
                    localTopicInfo, translations
            );
        }
    }
}
