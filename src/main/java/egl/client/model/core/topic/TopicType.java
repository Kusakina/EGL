package egl.client.model.core.topic;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import egl.client.model.core.DescribedEntity;
import egl.client.model.core.task.Task;
import egl.client.model.core.task.Test;
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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    Task theoryTask;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @Fetch(value = FetchMode.SUBSELECT)
    List<Task> tasks;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
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
