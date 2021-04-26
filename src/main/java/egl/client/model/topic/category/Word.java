package egl.client.model.topic.category;

import javax.persistence.Column;
import javax.persistence.Entity;

import egl.core.model.DatabaseData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Word extends DatabaseData {

    @Column
    private String text;

    @Column
    private Language language;

    @Override
    public String toString() {
        return text;
    }
}
