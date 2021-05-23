package egl.client.service.model.profile;

import egl.client.model.profile.GlobalCredentials;
import egl.client.repository.profile.GlobalCredentialsRepository;
import egl.client.service.model.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class GlobalCredentialsService extends AbstractEntityService<GlobalCredentials, GlobalCredentialsRepository> {

    public GlobalCredentialsService(GlobalCredentialsRepository repository) {
        super(repository);
    }
}
