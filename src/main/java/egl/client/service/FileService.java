package egl.client.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import egl.client.model.local.topic.category.Category;
import egl.client.model.local.topic.category.Translation;
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
            out.println(localTopicInfo.getGlobalId());
            for (Translation translation : category.getTranslations()) {
                var source = translation.getSource();
                var target = translation.getTarget();

                out.println(source.getText() + "\t" + target.getText());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
