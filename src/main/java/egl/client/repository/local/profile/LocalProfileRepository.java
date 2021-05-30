package egl.client.repository.local.profile;

import egl.client.model.core.profile.Profile;
import egl.client.repository.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalProfileRepository extends EntityRepository<Profile> { }
