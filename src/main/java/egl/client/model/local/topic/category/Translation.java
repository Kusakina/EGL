package egl.client.model.local.topic.category;

import egl.client.model.core.DatabaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Translation implements DatabaseEntity {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Word source;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Word target;

    @SuppressWarnings("CopyConstructorMissesField")
    public Translation(Translation other) {
        this(new Word(other.source), new Word(other.target));
    }

    public Translation(Word source, Word target) {
        this.source = source;
        this.target = target;
    }
}
