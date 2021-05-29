package egl.client.model.core.profile;

import egl.client.model.core.DescribedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public abstract class Profile extends DescribedEntity {

    protected Profile(String name, String description) {
        super(name, description);
    }
}