package egl.client.service.model.core;

import java.util.List;
import java.util.Optional;

import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.model.core.statistic.Result;
import egl.client.model.core.statistic.TaskStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import javafx.beans.property.Property;

public interface StatisticService {

    Property<Profile> selectedProfileProperty();
    Optional<Profile> getSelectedProfile();

    List<ProfileStatistic> findAllProfileStatistics();

    TaskStatistic findBy(TopicStatistic topicStatistic, Task task);
    Optional<TaskStatistic> tryFindBy(ProfileStatistic profileStatistic, Topic globalTopic, Task task);

    void update(TopicStatistic topicStatistic, Task task, Result result);
}
