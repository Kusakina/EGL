package egl.core.model.statistic;

import egl.core.model.DatabaseEntity;
import egl.core.model.profile.Profile;
import egl.core.model.topic.Topic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class ProfileStatistic extends DatabaseEntity {

    @ManyToOne
    private Profile profile;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    private Set<TopicStatistic> topicStatistics;

    public ProfileStatistic(Profile profile) {
        this.profile = profile;
        this.topicStatistics = new HashSet<>();
    }

    public Optional<TopicStatistic> getTopicStatisticFor(Topic topic) {
        return topicStatistics.stream()
                .filter(topicStatistic -> topicStatistic.getTopic().equals(topic))
                .findAny();
    }
}
