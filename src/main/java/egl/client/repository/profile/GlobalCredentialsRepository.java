package egl.client.repository.profile;

import egl.client.model.profile.GlobalCredentials;
import egl.client.repository.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalCredentialsRepository extends EntityRepository<GlobalCredentials> { }
