package egl.core.model.task;

import javax.persistence.Column;
import javax.persistence.Entity;

import egl.core.model.DescribedData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Task extends DescribedData {

    @Column
    private String sceneName;
}
