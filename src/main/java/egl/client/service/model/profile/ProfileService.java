package egl.client.service.model.profile;

import egl.client.repository.EntityRepository;
import egl.client.service.model.AbstractEntityService;
import egl.core.model.profile.Profile;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

public abstract class ProfileService<ProfileType extends Profile> extends AbstractEntityService<ProfileType, EntityRepository<ProfileType>> {

    private final Property<Profile> selectedProfileProperty;

    public ProfileService(EntityRepository<ProfileType> repository) {
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
