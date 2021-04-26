package egl.client.model.topic;

import javax.persistence.Column;
import javax.persistence.Entity;

import egl.core.model.DatabaseData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Theory extends DatabaseData {

    @Column
    private String text;
}
