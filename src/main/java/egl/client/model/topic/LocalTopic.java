package egl.client.model.topic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import egl.core.model.topic.Topic;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class LocalTopic extends Topic {

    @ManyToOne
    private Theory theory;

    @Column
    private long ratingId;
}
