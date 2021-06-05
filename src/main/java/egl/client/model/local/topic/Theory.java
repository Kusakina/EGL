package egl.client.model.local.topic;

import egl.client.model.core.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Theory extends DatabaseEntity {

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
