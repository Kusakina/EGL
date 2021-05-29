package egl.client.model.local.topic.category;

import javax.persistence.CascadeType;
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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Word source;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Word target;

    public Translation(Translation other) {
        this.source = new Word(other.source);
        this.target = new Word(other.target);
    }

    public Translation(Word source, Word target) {
        this.source = source;
        this.target = target;
    }
}
