package egl.client.service.model.global;

import egl.client.model.core.profile.Credentials;
import egl.client.model.core.profile.Profile;
import egl.client.repository.global.profile.GlobalCredentialsRepository;
import egl.client.service.model.EntityServiceException;
import egl.client.service.model.core.AbstractEntityService;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Optional<Credentials> findBy(String login, String password) {
        try {
            long passwordHash = Credentials.calculatePasswordHash(password);
            return repository.findAllByLoginAndPasswordHash(login, passwordHash)
                    .stream().findFirst();
        } catch (Exception e) {
            throw new EntityServiceException();
        }
    }
}
