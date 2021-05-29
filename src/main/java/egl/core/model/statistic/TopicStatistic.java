package egl.core.model.statistic;

import egl.client.model.core.DatabaseEntity;
import egl.core.model.task.Task;
import egl.core.model.topic.Topic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = {"profileStatistic"})
@Entity
@Data
@NoArgsConstructor
public class TopicStatistic extends DatabaseEntity {

    @ManyToOne
    private ProfileStatistic profileStatistic;

    @ManyToOne
    private Topic topic;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<TaskStatistic> taskStatistics;

    public TopicStatistic(ProfileStatistic profileStatistic, Topic topic) {
        this.profileStatistic = profileStatistic;
        this.topic = topic;
        this.taskStatistics = new HashSet<>();
    }

    public TaskStatistic getTaskStatisticFor(Task task) {
        return taskStatistics.stream()
                .filter(taskStatistic -> taskStatistic.getTask().equals(task))
                .findAny().orElse(new TaskStatistic(task, Result.NONE));
    }

    public boolean updateBy(Task task, Result result) {
        var taskStatistic = getTaskStatisticFor(task);

        boolean better = taskStatistic.updateBy(result);
        if (better) {
            taskStatistics.add(taskStatistic);
        }
        return better;
    }
}
