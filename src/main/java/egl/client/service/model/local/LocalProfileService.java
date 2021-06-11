package egl.client.service.model.local;

import egl.client.repository.local.profile.LocalProfileRepository;
import egl.client.service.model.core.ProfileService;
import org.springframework.stereotype.Service;

@Service
public class LocalProfileService extends ProfileService {

    public LocalProfileService(LocalProfileRepository repository) {
        super(repository);
    }
}
