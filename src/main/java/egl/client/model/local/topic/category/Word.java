package egl.client.model.local.topic.category;

import egl.client.model.core.DatabaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Word implements DatabaseEntity {

    @Id
    @GeneratedValue
    private long id;

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
