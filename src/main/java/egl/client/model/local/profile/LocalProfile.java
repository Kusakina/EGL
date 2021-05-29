package egl.client.model.local.profile;

import egl.core.model.profile.Profile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class LocalProfile extends Profile {

    public LocalProfile(String name, String description) {
        super(name, description);
    }
}
