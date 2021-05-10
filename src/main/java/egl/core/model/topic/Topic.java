package egl.core.model.topic;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import egl.core.model.DescribedEntity;
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

    public Topic(String name, String description, TopicType topicType) {
        super(name, description);
        this.topicType = topicType;
    }
}
