package egl.client.model.local.topic.category;

import egl.client.model.core.topic.TopicTasks;
import egl.client.model.local.topic.LocalTopic;
import egl.client.model.local.topic.Theory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Category extends LocalTopic {

    @Id
    @GeneratedValue
    private long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Translation> translations;

    public Category(Category other) {
        super(other);

        this.translations = other.getTranslations()
                .stream().map(Translation::new).collect(Collectors.toList());
    }

    public Category(String name, String description, TopicTasks topicTasks, Theory theory,
                    List<Translation> translations) {
        super(name, description, topicTasks, theory);
        this.translations = translations;
    }

    public List<Translation> getTranslations() {
        var translationsCopy = new ArrayList<Translation>();
        if (null != translations) translationsCopy.addAll(translations);
        return translationsCopy;
    }
}
