package egl.client.model.local.topic;

import egl.client.model.core.DatabaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Data
public class Theory implements DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "LONGTEXT")
    private String text;

    public Theory() {
        this.text = "";
    }

    @SuppressWarnings("CopyConstructorMissesField")
    public Theory(Theory other) {
        this(Optional.of(other).map(Theory::getText).orElse(""));
    }

    public Theory(String text) {
        this.text = text;
    }
}
