package egl.client.model.core.statistic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import egl.client.model.core.DatabaseEntity;
import egl.client.model.core.task.Task;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class TaskStatistic extends DatabaseEntity {

    @ManyToOne
    private TopicStatistic topicStatistic;

    @Column
    private String taskName;

    @Column
    private int correctAnswers;

    @Column
    private int totalAnswers;

    public TaskStatistic() {
        this(Result.NONE);
    }

    public TaskStatistic(TopicStatistic topicStatistic, Task task, Result result) {
        this(result);
        this.topicStatistic = topicStatistic;
        this.taskName = task.getName();
    }

    private TaskStatistic(Result result) {
        initBy(result);
    }

    private void initBy(Result result) {
        this.correctAnswers = result.getCorrectAnswers();
        this.totalAnswers = result.getTotalAnswers();
    }

    public Result getResult() {
        return new Result(correctAnswers, totalAnswers);
    }

    public boolean updateBy(Result result) {
        var better = getResult().compareTo(result) < 0;
        if (better) {
            initBy(result);
        }
        return better;
    }
}
