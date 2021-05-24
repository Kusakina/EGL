package egl.client.model.profile;

import javax.persistence.Entity;

import egl.core.model.profile.Credentials;
import egl.core.model.profile.Profile;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class GlobalCredentials extends Credentials {

    public GlobalCredentials(Profile profile) {
        super(profile);
    }
}
