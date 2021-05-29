package egl.client.model.core.task;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import egl.client.model.core.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Test extends DatabaseEntity {

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
