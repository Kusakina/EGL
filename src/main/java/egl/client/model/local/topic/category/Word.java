package egl.client.model.local.topic.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import egl.client.model.core.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Word extends DatabaseEntity {

    @Column
    private String text;

    @Column
    @Enumerated(EnumType.STRING)
    private Language language;

    public Word() {
        this.text = "";
    }

    public Word(Word other) {
        this(other.getText(), other.getLanguage());
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
