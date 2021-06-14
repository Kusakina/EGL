package egl.client.service.model.global;

import java.util.List;
import java.util.Optional;

import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.model.core.statistic.Result;
import egl.client.model.core.statistic.TaskStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import egl.client.model.local.topic.LocalTopicInfo;
import egl.client.service.model.core.StatisticServiceHolder;
import javafx.beans.property.Property;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class GlobalStatisticServiceHolder implements StatisticServiceHolder {

    private final GlobalStatisticService globalStatisticService;
    private final GlobalTopicService globalTopicService;

    public void registerTopic(LocalTopicInfo localTopicInfo) {
        globalStatisticService.getSelectedProfile().ifPresent(author ->
                globalTopicService.registerTopic(localTopicInfo, author)
        );
    }

    public List<ProfileStatistic> findAll() {
        return globalStatisticService.findAll();
    }

    public Optional<TaskStatistic> tryFindBy(ProfileStatistic profileStatistic, Topic globalTopic, Task task) {
        return globalStatisticService.tryFindBy(profileStatistic, globalTopic, task);
    }

    @Override
    public Optional<TopicStatistic> findBy(Topic localTopic) {
        return globalTopicService.findByLocal(localTopic)
                .flatMap(globalStatisticService::findBy);
    }

    @Override
    public TaskStatistic findBy(TopicStatistic topicStatistic, Task task) {
        return globalStatisticService.findBy(topicStatistic, task);
    }

    @Override
    public Property<Profile> selectedProfileProperty() {
        return globalStatisticService.selectedProfileProperty();
    }

    @Override
    public void update(TopicStatistic topicStatistic, Task task, Result result) {
        globalStatisticService.update(topicStatistic, task, result);
    }
}
