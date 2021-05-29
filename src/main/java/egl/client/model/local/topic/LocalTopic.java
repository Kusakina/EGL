package egl.client.model.local.topic;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public abstract class LocalTopic extends Topic {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Theory theory;

    @Column
    private long ratingId;

    public LocalTopic(LocalTopic other) {
        super(other);
        this.theory = new Theory(other.getTheory());
    }

    public LocalTopic(String name, String description, TopicType topicType, Theory theory) {
        super(name, description, topicType);
        this.theory = theory;
    }
}
