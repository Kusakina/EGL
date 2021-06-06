package egl.client.repository.global.profile;

import egl.client.model.core.profile.Profile;
import egl.client.repository.EntityRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalProfileRepository extends EntityRepository<Profile> { }
