package egl.client.service.model.core;

import java.util.Optional;

import egl.client.model.core.profile.Profile;
import egl.client.repository.core.profile.ProfileRepository;
import egl.client.service.model.AbstractEntityService;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

public abstract class ProfileService extends AbstractEntityService<Profile, ProfileRepository> {

    private final Property<Profile> selectedProfileProperty;

    public ProfileService(ProfileRepository repository) {
        super(repository);
        this.selectedProfileProperty = new SimpleObjectProperty<>();
    }

    public void select(Profile profile) {
        selectedProfileProperty.setValue(profile);
    }

    public Property<Profile> selectedProfileProperty() {
        return selectedProfileProperty;
    }

    public Optional<Profile> getSelectedProfile() {
        return Optional.ofNullable(
                selectedProfileProperty.getValue()
        );
    }
}
