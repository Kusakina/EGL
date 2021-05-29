package egl.client.model.core.profile;

import javax.persistence.CascadeType;
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
public abstract class Credentials extends DatabaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;

    private String login;
    private long passwordHash;

    protected Credentials(Profile profile) {
        this.profile = profile;
    }

    // TODO check maybe replace with better hash
    public static long calculatePasswordHash(String password) {
        return password.hashCode();
    }
}
