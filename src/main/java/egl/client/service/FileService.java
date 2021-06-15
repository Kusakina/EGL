package egl.client.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicType;
import egl.client.model.local.topic.LocalTopicInfo;
import egl.client.model.local.topic.Theory;
import egl.client.model.local.topic.category.Category;
import egl.client.model.local.topic.category.Language;
import egl.client.model.local.topic.category.Translation;
import egl.client.model.local.topic.category.Word;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    public static void save(Category category, File file) {
        var localTopicInfo = category.getLocalTopicInfo();
        var topic = localTopicInfo.getTopic();

        try (PrintWriter out = new PrintWriter(file)) {
            out.println(topic.getName());
            out.println(topic.getDescription().replaceAll("\n", "; "));
            out.println(localTopicInfo.getTheory().getText().replaceAll("\n", "; "));
            for (Translation translation : category.getTranslations()) {
                var source = translation.getSource();
                var target = translation.getTarget();

                out.println(source.getText() + "\t" + target.getText());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Category loadCategory(File file) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
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
