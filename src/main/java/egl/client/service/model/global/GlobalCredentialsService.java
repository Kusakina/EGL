package egl.client.service.model.global;

import java.util.Optional;

import egl.client.model.core.profile.Credentials;
import egl.client.model.core.profile.Profile;
import egl.client.repository.global.profile.GlobalCredentialsRepository;
import egl.client.service.model.EntityServiceException;
import egl.client.service.model.core.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class GlobalCredentialsService extends AbstractEntityService<Credentials, GlobalCredentialsRepository> {

    public GlobalCredentialsService(GlobalCredentialsRepository repository) {
        super(repository);
    }

    public Optional<Credentials> findBy(Profile profile) {
        try {
            return repository.findByProfile(profile);
        } catch (Exception e) {
            throw new EntityServiceException();
        }
    }

    public Optional<Credentials> findBy(String login) {
        try {
            return repository.findByLogin(login);
        } catch (Exception e) {
            throw new EntityServiceException();
        }
    }
}
