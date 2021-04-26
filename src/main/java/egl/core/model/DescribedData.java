package egl.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class DescribedData extends DatabaseData {

    @Column
    private String name;

    @Column
    private String description;
}
