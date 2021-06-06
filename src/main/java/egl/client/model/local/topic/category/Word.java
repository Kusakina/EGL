package egl.client.model.local.topic.category;

import egl.client.model.core.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

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
