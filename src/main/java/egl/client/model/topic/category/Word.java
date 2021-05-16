package egl.client.model.topic.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import egl.core.model.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Word extends DatabaseEntity {

    @Column
    private String text;

    @Column
    @Enumerated(EnumType.STRING)
    private Language language;

    public Word(Word other) {
        if (null != other) {
            this.text = other.text;
            this.language = other.language;
        }
    }

    public Word(String text, Language language) {
        this.text = text;
        this.language = language;
    }

    @Override
    public String toString() {
        return text;
    }
}
