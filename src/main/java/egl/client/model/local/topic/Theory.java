package egl.client.model.local.topic;

import egl.client.model.core.DatabaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
public class Theory implements DatabaseEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(columnDefinition = "LONGTEXT")
    private String text;

    @SuppressWarnings("CopyConstructorMissesField")
    public Theory(Theory other) {
        this(Optional.of(other).map(Theory::getText).orElse(""));
    }

    public Theory(String text) {
        this.text = text;
    }
}
