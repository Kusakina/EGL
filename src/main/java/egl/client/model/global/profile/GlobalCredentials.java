package egl.client.model.global.profile;

import egl.core.model.profile.Credentials;
import egl.core.model.profile.Profile;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class GlobalCredentials extends Credentials {

    public GlobalCredentials(Profile profile) {
        super(profile);
    }
}
