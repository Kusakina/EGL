package egl.client.service.model.profile;

import egl.client.repository.local.profile.LocalProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class LocalProfileService extends ProfileService {

    public LocalProfileService(LocalProfileRepository repository) {
        super(repository);
    }
}
