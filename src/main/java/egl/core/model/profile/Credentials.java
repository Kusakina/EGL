package egl.core.model.profile;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import egl.core.model.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public abstract class Credentials extends DatabaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;

    private String login;
    private long passwordHash;
}
