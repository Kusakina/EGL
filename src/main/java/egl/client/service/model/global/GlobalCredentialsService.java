package egl.client.service.model.global;

import egl.client.model.core.profile.Credentials;
import egl.client.model.core.profile.Profile;
import egl.client.repository.global.profile.GlobalCredentialsRepository;
import egl.client.service.model.EntityServiceException;
import egl.client.service.model.core.AbstractEntityService;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

@Service
public class GlobalCredentialsService extends AbstractEntityService<Credentials, GlobalCredentialsRepository> {

    public GlobalCredentialsService(GlobalCredentialsRepository repository) {
        super(repository);
    }

    public Credentials findBy(Profile profile) {
        return repository.findByProfile(profile);
    }

    public Credentials findBy(String login) throws EntityServiceException {
        try {
            return repository.findByLogin(login);
        } catch (DataAccessResourceFailureException e) {
            throw new EntityServiceException(e);
        }
    }
}
