package egl.client.model.local.topic.category;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import egl.client.model.core.DatabaseEntity;
import egl.client.model.core.topic.TopicHashCodeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Translation extends DatabaseEntity implements TopicHashCodeEntity {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Word source;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Word target;

    public Translation(Translation other) {
        this(other.getSource(), other.getTarget());
    }

    public Translation(Word source, Word target) {
        this.source = new Word(source);
        this.target = new Word(target);
    }

    @Override
    public long getTopicHashCode() {
        return source.getTopicHashCode() * 1234567 + target.getTopicHashCode();
    }
}
