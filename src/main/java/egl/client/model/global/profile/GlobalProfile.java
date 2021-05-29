package egl.client.model.global.profile;

import egl.client.model.core.profile.Profile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class GlobalProfile extends Profile {

    public GlobalProfile(String name, String description) {
        super(name, description);
    }
}
