package egl.client.service.model.profile;

import egl.client.repository.EntityRepository;
import egl.client.service.model.AbstractEntityService;
import egl.core.model.profile.Profile;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

public abstract class ProfileService<ProfileType extends Profile> extends AbstractEntityService<ProfileType, EntityRepository<ProfileType>> {

    private final Property<ProfileType> selectedProfileProperty;

    public ProfileService(EntityRepository<ProfileType> repository) {
        super(repository);
        this.selectedProfileProperty = new SimpleObjectProperty<>();
    }

    public void select(ProfileType profile) {
        selectedProfileProperty.setValue(profile);
    }

    public Property<ProfileType> selectedProfileProperty() {
        return selectedProfileProperty;
    }

    public ProfileType getSelectedProfile() {
        return selectedProfileProperty.getValue();
    }
}
