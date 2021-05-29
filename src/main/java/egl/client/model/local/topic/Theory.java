package egl.client.model.local.topic;

import javax.persistence.Column;
import javax.persistence.Entity;

import egl.client.model.core.DatabaseEntity;
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

    public Theory(Theory other) {
        this.text = (null != other) ? other.text : "";
    }

    public Theory(String text) {
        this.text = text;
    }
}
