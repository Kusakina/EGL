package egl.client.service.model.profile;

import egl.client.model.local.profile.LocalProfile;
import egl.client.repository.profile.LocalProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class LocalProfileService extends ProfileService<LocalProfile> {

    public LocalProfileService(LocalProfileRepository repository) {
        super(repository);
    }
}
