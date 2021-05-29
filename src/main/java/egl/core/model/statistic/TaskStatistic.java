package egl.core.model.statistic;

import egl.client.model.core.DatabaseEntity;
import egl.client.model.core.task.Task;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class TaskStatistic extends DatabaseEntity {

    @ManyToOne
    private Task task;

    @OneToOne(cascade = CascadeType.ALL)
    private Result result;

    public TaskStatistic(Task task, Result result) {
        this.task = task;
        this.result = result;
    }

    public Result getResult() {
        return Optional.ofNullable(result).orElse(Result.NONE);
    }

    public boolean updateBy(Result result) {
        var better = getResult().compareTo(result) < 0;
        if (better) {
            this.result = result;
        }
        return better;
    }
}
