package egl.client.repository.profile;

import egl.client.model.profile.LocalProfile;
import egl.client.repository.DatabaseDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalProfileRepository extends DatabaseDataRepository<LocalProfile> { }
