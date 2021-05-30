package egl.client.model.core.statistic;

import egl.client.model.core.DatabaseEntity;
import egl.client.model.core.profile.Profile;
import egl.client.model.core.topic.Topic;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class ProfileStatistic implements DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Profile profile;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
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
