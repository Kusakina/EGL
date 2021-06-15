package egl.client.model.global.topic;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class GlobalTopicInfo extends TopicInfo {

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Topic topic;

    public GlobalTopicInfo(
            Topic topic,
            long localHashCode) {
        super(localHashCode);
        this.topic = new Topic(topic);
    }
}
