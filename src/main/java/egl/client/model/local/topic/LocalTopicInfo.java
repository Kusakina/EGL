package egl.client.model.local.topic;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import egl.client.model.core.DatabaseEntity;
import egl.client.model.core.topic.Topic;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class LocalTopicInfo extends DatabaseEntity implements GlobalHashCodeEntity {

    public static final long NO_GLOBAL_ID = -1;

    @OneToOne(cascade = CascadeType.ALL)
    private Topic topic;

    @OneToOne(cascade = CascadeType.ALL)
    private Theory theory;

    @Column
    private long globalId;

    @Column
    private int globalHashCode;

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
        this.globalId = NO_GLOBAL_ID;
        this.globalHashCode = 0;
    }
}
