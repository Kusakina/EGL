package egl.client.model.topic;

import javax.persistence.Column;
import javax.persistence.Entity;

import egl.core.model.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Theory extends DatabaseEntity {

    @Column(columnDefinition = "LONGTEXT")
    private String text;

    public Theory(String text) {
        this.text = text;
    }
}
