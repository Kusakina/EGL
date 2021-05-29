package egl.client.repository.profile;

import egl.client.model.local.profile.LocalProfile;
import egl.client.repository.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalProfileRepository extends EntityRepository<LocalProfile> { }
