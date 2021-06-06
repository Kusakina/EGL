package egl.client.model.core;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Getter
@Setter
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

    @Override
    public String toString() {
        return name;
    }
}
