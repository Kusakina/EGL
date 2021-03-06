package egl.client.model.core.task;

import javax.persistence.Column;
import javax.persistence.Entity;

import egl.client.model.core.DescribedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Task extends DescribedEntity {

    @Column
    private String sceneName;

    public Task(Task other) {
        this(other.getName(), other.getDescription(), other.getSceneName());
    }

    public Task(String name, String description, String sceneName) {
        super(name, description);
        this.sceneName = sceneName;
    }
}
