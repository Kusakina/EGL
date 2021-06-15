package egl.client.model.local.topic;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class LocalTopicInfo extends TopicInfo {

    @OneToOne(cascade = CascadeType.ALL)
    private Topic topic;

    @OneToOne(cascade = CascadeType.ALL)
    private Theory theory;

    public LocalTopicInfo() {
        this(new Topic(), new Theory());
    }

    @SuppressWarnings("CopyConstructorMissesField")
    public LocalTopicInfo(LocalTopicInfo other) {
        this(
            new Topic(other.getTopic()),
            new Theory(other.getTheory())
        );
    }

    public LocalTopicInfo(Topic topic, Theory theory) {
        super(0);
        this.topic = topic;
        this.theory = theory;
    }
}
