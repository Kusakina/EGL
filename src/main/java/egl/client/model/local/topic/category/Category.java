package egl.client.model.local.topic.category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import egl.client.model.local.topic.LocalTopic;
import egl.client.model.local.topic.Theory;
import egl.client.model.core.topic.TopicType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Category extends LocalTopic {

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Translation> translations;

    public Category(Category other) {
        super(other);

        this.translations = other.getTranslations()
                .stream().map(Translation::new).collect(Collectors.toList());
    }

    public Category(String name, String description, TopicType topicType, Theory theory,
                    List<Translation> translations) {
        super(name, description, topicType, theory);
        this.translations = translations;
    }

    public List<Translation> getTranslations() {
        var translationsCopy = new ArrayList<Translation>();
        if (null != translations) translationsCopy.addAll(translations);
        return translationsCopy;
    }
}
