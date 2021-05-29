package egl.client.model.core.topic;

import egl.client.model.core.DescribedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public abstract class Topic extends DescribedEntity {

    @ManyToOne
    TopicTasks topicTasks;

    public Topic(Topic other){
        super(other);
        this.topicTasks = other.topicTasks;
    }

    public Topic(String name, String description, TopicTasks topicTasks) {
        super(name, description);
        this.topicTasks = topicTasks;
    }
}
