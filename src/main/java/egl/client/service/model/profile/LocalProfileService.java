package egl.client.service.model.profile;

import egl.client.model.profile.LocalProfile;
import egl.client.repository.profile.LocalProfileRepository;
import egl.client.service.model.AbstractEntityService;

public class LocalProfileService extends AbstractEntityService<LocalProfile, LocalProfileRepository> {

    public LocalProfileService(LocalProfileRepository repository) {
        super(repository);
    }
}
