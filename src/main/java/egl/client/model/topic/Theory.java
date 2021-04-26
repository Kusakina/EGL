package egl.client.model.topic;

import javax.persistence.Column;
import javax.persistence.Entity;

import egl.core.model.DatabaseData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Theory extends DatabaseData {

    @Column
    private String text;

    public Theory(String text) {
        this.text = text;
    }
}
