package egl.client.model.core.statistic;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import egl.client.model.core.DatabaseEntity;
import egl.client.model.core.topic.Topic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class TopicStatistic extends DatabaseEntity {

    @ManyToOne
    private ProfileStatistic profileStatistic;

    @ManyToOne
    private Topic topic;

    public TopicStatistic(ProfileStatistic profileStatistic, Topic topic) {
        this();
        this.profileStatistic = profileStatistic;
        this.topic = topic;
    }
}
