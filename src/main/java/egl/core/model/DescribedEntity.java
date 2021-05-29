package egl.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import egl.client.model.core.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public abstract class DescribedEntity extends DatabaseEntity {

    @Column
    private String name;

    @Column
    private String description;

    public DescribedEntity(DescribedEntity other) {
        this(other.getName(), other.getDescription());
    }

    protected DescribedEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
