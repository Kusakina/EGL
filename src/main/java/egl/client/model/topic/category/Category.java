package egl.client.model.topic.category;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import egl.client.model.topic.LocalTopic;
import egl.client.model.topic.Theory;
import egl.core.model.topic.TopicType;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Translation> translations;

    public Category(String name, String description, TopicType topicType, Theory theory,
                    List<Translation> translations) {
        super(name, description, topicType, theory);
        this.translations = translations;
    }
}
