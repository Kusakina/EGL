package egl.client.model.topic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import egl.core.model.topic.Topic;
import egl.core.model.topic.TopicType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public abstract class LocalTopic extends Topic {

    @ManyToOne
    private Theory theory;

    @Column
    private long ratingId;

    public LocalTopic(String name, String description, TopicType topicType, Theory theory) {
        super(name, description, topicType);
        this.theory = theory;
    }
}
