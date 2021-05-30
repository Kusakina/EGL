package egl.client.service.model.profile;

import egl.client.model.core.profile.Profile;
import egl.client.repository.global.profile.GlobalProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class GlobalProfileService extends ProfileService<Profile> {

    public GlobalProfileService(GlobalProfileRepository repository) {
        super(repository);
    }
}
