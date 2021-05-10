package egl.client.service.model.profile;

import egl.client.model.profile.LocalProfile;
import egl.client.repository.profile.LocalProfileRepository;
import egl.client.service.model.AbstractEntityService;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import org.springframework.stereotype.Service;

@Service
public class LocalProfileService extends AbstractEntityService<LocalProfile, LocalProfileRepository> {

    private final Property<LocalProfile> selectedProfileProperty;

    public LocalProfileService(LocalProfileRepository repository) {
        super(repository);
        this.selectedProfileProperty = new SimpleObjectProperty<>();
    }

    public void select(LocalProfile profile) {
        selectedProfileProperty.setValue(profile);
    }

    public Property<LocalProfile> selectedProfileProperty() {
        return selectedProfileProperty;
    }

    public LocalProfile getSelectedProfile() {
        return selectedProfileProperty.getValue();
    }
}
