package egl.client.model.core.statistic;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import egl.client.model.core.DatabaseEntity;
import egl.client.model.core.profile.Profile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class ProfileStatistic extends DatabaseEntity {

    @ManyToOne
    private Profile profile;

    public ProfileStatistic(Profile profile) {
        this();
        this.profile = profile;
    }
}
