package egl.client.service.model.profile;

import egl.client.model.global.profile.GlobalProfile;
import egl.client.repository.profile.GlobalProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class GlobalProfileService extends ProfileService<GlobalProfile> {

    public GlobalProfileService(GlobalProfileRepository repository) {
        super(repository);
    }
}
