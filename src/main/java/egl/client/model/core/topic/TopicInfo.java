package egl.client.model.core.topic;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import egl.client.model.core.DatabaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class TopicInfo extends DatabaseEntity implements TopicHashCodeEntity {

    public abstract Topic getTopic();

    @Column
    private long topicHashCode;

    protected TopicInfo(long topicHashCode) {
        this.topicHashCode = topicHashCode;
    }
}
