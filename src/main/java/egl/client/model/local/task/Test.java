package egl.client.model.local.task;

import egl.client.model.core.DatabaseEntity;
import egl.client.model.core.task.Task;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Test implements DatabaseEntity {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Task task;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Task> innerTasks;

    public Test(Task task, List<Task> innerTasks) {
        this.task = task;
        this.innerTasks = innerTasks;
    }

    public List<Task> getInnerTasks() {
        return new ArrayList<>(innerTasks);
    }
}
