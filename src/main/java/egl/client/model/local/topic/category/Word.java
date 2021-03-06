package egl.client.model.local.topic.category;

import egl.client.model.core.DatabaseEntity;
import egl.client.model.core.topic.TopicHashCodeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Word extends DatabaseEntity implements TopicHashCodeEntity {

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
    public long getTopicHashCode() {
        long hashCode = 0;
        for (char ch : text.toCharArray()) {
            hashCode *= 2345671;
            hashCode += ch;
        }
        hashCode <<= 8;
        hashCode |= language.getStartLetter();

        return hashCode;
    }

    public void updateLanguage() {
        this.language = Language.of(text);
    }
}
