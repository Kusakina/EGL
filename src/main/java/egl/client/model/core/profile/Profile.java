package egl.client.model.core.profile;

import egl.client.model.core.DescribedEntity;
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
public class Profile extends DescribedEntity {

    @Id
    @GeneratedValue
    private long id;

    public Profile(String name, String description) {
        super(name, description);
    }
}
