package egl.client.model.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class DescribedEntity extends DatabaseEntity {

    @Column
    private String name;

    @Column
    private String description;

    protected DescribedEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
