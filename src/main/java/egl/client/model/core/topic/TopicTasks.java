package egl.client.model.core.topic;

import egl.client.model.core.DescribedEntity;
import egl.client.model.core.task.Task;
import egl.client.model.core.task.Test;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class TopicTasks extends DescribedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Enumerated(EnumType.STRING)
    private TopicType topicType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    Task theoryTask;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @Fetch(value = FetchMode.SUBSELECT)
    List<Task> tasks;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    Test test;

    public TopicTasks(String name, String description, TopicType topicType, Task theoryTask, List<Task> tasks, Test test) {
        super(name, description);
        this.topicType = topicType;
        this.theoryTask = theoryTask;
        this.tasks = tasks;
        this.test = test;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}
