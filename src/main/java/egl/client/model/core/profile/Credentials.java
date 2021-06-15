package egl.client.model.core.profile;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import egl.client.model.core.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Credentials extends DatabaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;

    @Column
    private String login;

    @Column
    private long passwordHash;

    public Credentials(Profile profile) {
        this.profile = profile;
    }

    public static long calculatePasswordHash(String password) {
        return password.hashCode();
    }
}
