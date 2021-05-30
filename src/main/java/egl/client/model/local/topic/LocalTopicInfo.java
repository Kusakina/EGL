package egl.client.model.local.topic;

import egl.client.model.core.DatabaseEntity;
import egl.client.model.core.topic.Topic;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class LocalTopicInfo implements DatabaseEntity {

    private static final long NO_GLOBAL_ID = -1;

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Topic topic;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Theory theory;

    @Column
    private long ratingId;

    public LocalTopicInfo() {
        this.topic = new Topic();
        this.theory = new Theory();
    }

    @SuppressWarnings("CopyConstructorMissesField")
    public LocalTopicInfo(LocalTopicInfo other) {
        this(
            new Topic(other.getTopic()),
            new Theory(other.getTheory())
        );
    }

    public LocalTopicInfo(Topic topic, Theory theory) {
        this.topic = topic;
        this.theory = theory;
        this.ratingId = NO_GLOBAL_ID;
    }
}
