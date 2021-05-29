package egl.core.model.topic;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import egl.client.model.core.DescribedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public abstract class Topic extends DescribedEntity {

    @ManyToOne
    TopicType topicType;

    public Topic(Topic other){
        super(other);
        this.topicType = other.topicType;
    }

    public Topic(String name, String description, TopicType topicType) {
        super(name, description);
        this.topicType = topicType;
    }
}
