package egl.core.model.task;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Test extends Task {

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Task> tasks;

    public Test(String name, String description, String sceneName, List<Task> tasks) {
        super(name, description, sceneName);
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}
