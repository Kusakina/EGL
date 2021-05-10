package egl.core.model.topic;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import egl.core.model.DescribedEntity;
import egl.core.model.task.Task;
import egl.core.model.task.Test;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class TopicType extends DescribedEntity {

    @ManyToOne
    Task theoryTask;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    List<Task> tasks;

    @ManyToOne
    Test test;

    public TopicType(String name, String description, Task theoryTask, List<Task> tasks, Test test) {
        super(name, description);
        this.theoryTask = theoryTask;
        this.tasks = tasks;
        this.test = test;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}
