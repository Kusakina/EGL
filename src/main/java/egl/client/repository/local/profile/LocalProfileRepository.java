package egl.client.repository.local.profile;

import egl.client.model.core.profile.Profile;
import egl.client.repository.core.profile.ProfileRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalProfileRepository extends ProfileRepository,
        JpaRepository<Profile, Long> { }
