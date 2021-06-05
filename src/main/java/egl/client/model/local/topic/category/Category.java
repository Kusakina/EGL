package egl.client.model.local.topic.category;

import egl.client.model.core.DatabaseEntity;
import egl.client.model.local.topic.LocalTopicInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Category extends DatabaseEntity {

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private LocalTopicInfo localTopicInfo;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Translation> translations;

    public Category() {
        this.localTopicInfo = new LocalTopicInfo();
        this.translations = new ArrayList<>();
    }

    public Category(Category other) {
        this.localTopicInfo = new LocalTopicInfo(other.getLocalTopicInfo());
        this.translations = other.getTranslations()
                .stream().map(Translation::new).collect(Collectors.toList());
    }

    public Category(LocalTopicInfo localTopicInfo,
                    List<Translation> translations) {
        this.localTopicInfo = localTopicInfo;
        this.translations = translations;
    }

    public List<Translation> getTranslations() {
        var translationsCopy = new ArrayList<Translation>();
        if (null != translations) translationsCopy.addAll(translations);
        return translationsCopy;
    }
}
