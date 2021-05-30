package egl.client.service.model.profile;

import egl.client.model.core.profile.Profile;
import egl.client.repository.local.profile.LocalProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class LocalProfileService extends ProfileService<Profile> {

    public LocalProfileService(LocalProfileRepository repository) {
        super(repository);
    }
}
