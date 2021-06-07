package egl.client.model.core.statistic;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import egl.client.model.core.DatabaseEntity;
import egl.client.model.core.profile.Profile;
import egl.client.model.core.topic.Topic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class ProfileStatistic extends DatabaseEntity {

    @ManyToOne
    private Profile profile;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<TopicStatistic> topicStatistics;

    public ProfileStatistic() {
        this.topicStatistics = new HashSet<>();
    }

    public ProfileStatistic(Profile profile) {
        this();
        this.profile = profile;
    }

    public Optional<TopicStatistic> getTopicStatisticFor(Topic topic) {
        return topicStatistics.stream()
                .filter(topicStatistic -> topicStatistic.getTopic().equals(topic))
                .findAny();
    }
}
