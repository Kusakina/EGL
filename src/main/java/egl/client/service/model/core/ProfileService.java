package egl.client.service.model.core;

import egl.client.model.core.profile.Profile;
import egl.client.service.model.AbstractEntityService;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class ProfileService extends AbstractEntityService<Profile, JpaRepository<Profile, Long>> {

    private final Property<Profile> selectedProfileProperty;

    public ProfileService(JpaRepository<Profile, Long> repository) {
        super(repository);
        this.selectedProfileProperty = new SimpleObjectProperty<>();
    }

    public void select(Profile profile) {
        selectedProfileProperty.setValue(profile);
    }

    public Property<Profile> selectedProfileProperty() {
        return selectedProfileProperty;
    }

    public Profile getSelectedProfile() {
        return selectedProfileProperty.getValue();
    }
}
