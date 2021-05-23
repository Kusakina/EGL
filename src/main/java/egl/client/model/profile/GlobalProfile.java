package egl.client.model.profile;

import egl.core.model.profile.Profile;
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
