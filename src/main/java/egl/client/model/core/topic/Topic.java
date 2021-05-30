package egl.client.model.core.topic;

import egl.client.model.core.DescribedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Topic extends DescribedEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    @Enumerated(EnumType.STRING)
    private TopicType topicType;

    @SuppressWarnings("CopyConstructorMissesField")
    public Topic(Topic other){
        this(other.getName(), other.getDescription(), other.topicType);
    }

    public Topic(String name, String description, TopicType topicType) {
        super(name, description);
        this.topicType = topicType;
    }
}
