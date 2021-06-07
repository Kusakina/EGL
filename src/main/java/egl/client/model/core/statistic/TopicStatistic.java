package egl.client.model.core.statistic;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import egl.client.model.core.DatabaseEntity;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@EqualsAndHashCode(callSuper = true, exclude = "profileStatistic")
@Entity
@Data
public class TopicStatistic extends DatabaseEntity {

    @ManyToOne
    private ProfileStatistic profileStatistic;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Topic topic;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<TaskStatistic> taskStatistics;

    public TopicStatistic() {
        this.taskStatistics = new HashSet<>();
    }

    public TopicStatistic(ProfileStatistic profileStatistic, Topic topic) {
        this();
        this.profileStatistic = profileStatistic;
        this.topic = topic;
    }

    private boolean compareStatisticTask(TaskStatistic taskStatistic, Task task) {
        // scene name is unique
        return taskStatistic.getTask().getSceneName()
                .equals(task.getSceneName());
    }

    public TaskStatistic getTaskStatisticFor(Task task) {
        return taskStatistics.stream()
                .filter(taskStatistic -> compareStatisticTask(taskStatistic, task))
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
