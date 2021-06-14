package egl.client.service.model.core;

import java.util.Optional;

import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.Result;
import egl.client.model.core.statistic.TaskStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import javafx.beans.property.Property;

public interface StatisticServiceHolder {

    Optional<TopicStatistic> findBy(Topic localTopic);
    TaskStatistic findBy(TopicStatistic topicStatistic, Task task);
    Property<Profile> selectedProfileProperty();
    void update(TopicStatistic topicStatistic, Task task, Result result);
}
