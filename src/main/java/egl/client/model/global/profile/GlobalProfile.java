package egl.client.model.global.profile;

import egl.client.model.core.profile.Profile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class GlobalProfile extends Profile {

    @Id
    @GeneratedValue
    private long id;

    public GlobalProfile(String name, String description) {
        super(name, description);
    }
}
