package egl.client.model.core.task;

import egl.client.model.core.DescribedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Task extends DescribedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String sceneName;

    public Task(String name, String description, String sceneName) {
        super(name, description);
        this.sceneName = sceneName;
    }
}
