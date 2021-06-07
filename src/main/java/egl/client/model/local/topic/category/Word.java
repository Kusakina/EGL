package egl.client.model.local.topic.category;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import egl.client.model.core.DatabaseEntity;
import egl.client.model.local.topic.GlobalHashCodeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Word extends DatabaseEntity implements GlobalHashCodeEntity {

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

    @Override
    public int getGlobalHashCode() {
        return Objects.hash(text, language);
    }
}
