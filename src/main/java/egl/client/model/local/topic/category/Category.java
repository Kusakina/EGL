package egl.client.model.local.topic.category;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import egl.client.model.core.DatabaseEntity;
import egl.client.model.local.topic.LocalTopicInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Category extends DatabaseEntity {

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private LocalTopicInfo localTopicInfo;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<Translation> translations;

    private Category(LocalTopicInfo localTopicInfo) {
        this.localTopicInfo = localTopicInfo;
        this.translations = new LinkedHashSet<>();
    }

    public Category() {
        this(new LocalTopicInfo());
    }

    public Category(Category other) {
        this.localTopicInfo = new LocalTopicInfo(other.getLocalTopicInfo());
        this.translations = other.getTranslations()
                .stream().map(Translation::new).collect(Collectors.toSet());
    }

    public Category(LocalTopicInfo localTopicInfo,
                    Collection<Translation> translations) {
        this(localTopicInfo);
        this.translations.addAll(translations);
        this.localTopicInfo.setGlobalHashCode(
                this.translations.hashCode()
        );
    }

    public List<Translation> getTranslations() {
        var translationsCopy = new ArrayList<Translation>();
        if (null != translations) translationsCopy.addAll(translations);
        return translationsCopy;
    }
}
