package egl.client.model.core.profile;

import egl.client.model.core.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Credentials extends DatabaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;

    private String login;
    private long passwordHash;

    public Credentials(Profile profile) {
        this.profile = profile;
    }

    // TODO check maybe replace with better hash
    public static long calculatePasswordHash(String password) {
        return password.hashCode();
    }
}
