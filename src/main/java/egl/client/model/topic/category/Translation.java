package egl.client.model.topic.category;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import egl.core.model.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Translation extends DatabaseEntity {

    @ManyToOne
    private Word source;

    @ManyToOne
    private Word target;

    public Translation(Word source, Word target) {
        this.source = source;
        this.target = target;
    }
}
