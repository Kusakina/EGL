package egl.core.model.topic;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import egl.core.model.DescribedData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Topic extends DescribedData {

    @ManyToOne
    TopicType topicType;
}
