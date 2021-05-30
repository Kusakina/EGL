package egl.client.service.model.profile;

import egl.client.model.core.profile.Profile;
import egl.client.repository.EntityRepository;
import egl.client.service.model.AbstractEntityService;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

public abstract class ProfileService extends AbstractEntityService<Profile, EntityRepository<Profile>> {

    private final Property<Profile> selectedProfileProperty;

    public ProfileService(EntityRepository<Profile> repository) {
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
