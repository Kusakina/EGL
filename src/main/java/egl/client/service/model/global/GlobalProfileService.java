package egl.client.service.model.global;

import egl.client.repository.global.profile.GlobalProfileRepository;
import egl.client.service.model.core.ProfileService;
import org.springframework.stereotype.Service;

@Service
public class GlobalProfileService extends ProfileService {

    public GlobalProfileService(GlobalProfileRepository repository) {
        super(repository);
    }
}
