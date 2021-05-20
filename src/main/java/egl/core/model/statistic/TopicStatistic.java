package egl.core.model.statistic;

import egl.core.model.DatabaseEntity;
import egl.core.model.task.Task;
import egl.core.model.topic.Topic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class TopicStatistic extends DatabaseEntity {

    @ManyToOne
    private ProfileStatistic profileStatistic;

    @ManyToOne
    private Topic topic;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
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
