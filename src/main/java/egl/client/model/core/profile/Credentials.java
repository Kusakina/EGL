package egl.client.model.core.profile;

import egl.client.model.core.DatabaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public abstract class Credentials implements DatabaseEntity {

    @Id
    @GeneratedValue
    private long id;

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
