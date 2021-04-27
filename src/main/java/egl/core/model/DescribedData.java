package egl.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public abstract class DescribedData extends DatabaseData {

    @Column
    private String name;

    @Column
    private String description;

    protected DescribedData(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
