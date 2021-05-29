package egl.client.model.core.statistic;

import egl.client.model.core.DatabaseEntity;
import egl.client.model.core.task.Task;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
public class TaskStatistic implements DatabaseEntity {

    @Id
    @GeneratedValue
    private long id;

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
