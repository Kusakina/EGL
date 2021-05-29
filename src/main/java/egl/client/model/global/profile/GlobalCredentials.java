package egl.client.model.global.profile;

import egl.client.model.core.profile.Credentials;
import egl.client.model.core.profile.Profile;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class GlobalCredentials extends Credentials {

    public GlobalCredentials(Profile profile) {
        super(profile);
    }
}
