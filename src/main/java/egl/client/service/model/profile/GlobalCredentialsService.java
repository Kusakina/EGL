package egl.client.service.model.profile;

import egl.client.model.core.profile.Credentials;
import egl.client.model.core.profile.Profile;
import egl.client.repository.global.profile.GlobalCredentialsRepository;
import egl.client.service.model.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class GlobalCredentialsService extends AbstractEntityService<Credentials, GlobalCredentialsRepository> {

    public GlobalCredentialsService(GlobalCredentialsRepository repository) {
        super(repository);
    }

    public Credentials findBy(Profile profile) {
        return repository.findByProfile(profile);
    }

    public Credentials findBy(String login) {
        return repository.findByLogin(login);
    }
}
