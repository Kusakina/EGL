package egl.client.model.local.topic.category;

import egl.client.model.core.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Translation extends DatabaseEntity {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Word source;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Word target;

    public Translation(Translation other) {
        this(other.getSource(), other.getTarget());
    }

    public Translation(Word source, Word target) {
        this.source = new Word(source);
        this.target = new Word(target);
    }
}
