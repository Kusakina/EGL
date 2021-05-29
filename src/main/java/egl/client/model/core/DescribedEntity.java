package egl.client.model.core;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class DescribedEntity implements DatabaseEntity {

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
