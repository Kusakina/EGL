package egl.client.model.core.topic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import egl.client.model.core.DescribedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Topic extends DescribedEntity {

    @Column
    @Enumerated(EnumType.STRING)
    private TopicType topicType;

    public Topic(Topic other){
        this(other.getName(), other.getDescription(), other.topicType);
    }

    public Topic(String name, String description, TopicType topicType) {
        super(name, description);
        this.topicType = topicType;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
