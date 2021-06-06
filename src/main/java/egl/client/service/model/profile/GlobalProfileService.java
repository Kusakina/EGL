package egl.client.service.model.profile;

import egl.client.repository.global.profile.GlobalProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class GlobalProfileService extends ProfileService {

    public GlobalProfileService(GlobalProfileRepository repository) {
        super(repository);
    }
}
