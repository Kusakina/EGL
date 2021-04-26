package egl.client.model.topic.category;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import egl.core.model.DatabaseData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Translation extends DatabaseData {

    @ManyToOne
    private Word source;

    @ManyToOne
    private Word target;
}
