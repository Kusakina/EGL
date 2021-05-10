package egl.core.model.task;

import javax.persistence.Column;
import javax.persistence.Entity;

import egl.core.model.DescribedEntity;
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

    public Task(String name, String description, String sceneName) {
        super(name, description);
        this.sceneName = sceneName;
    }
}
