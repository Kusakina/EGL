package egl.client.repository.profile;

import egl.client.model.profile.LocalProfile;
import egl.client.repository.DatabaseEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalProfileRepository extends DatabaseEntityRepository<LocalProfile> { }
