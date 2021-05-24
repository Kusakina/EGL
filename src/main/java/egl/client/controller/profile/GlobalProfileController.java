package egl.client.controller.profile;

import egl.client.model.profile.GlobalCredentials;
import egl.client.model.profile.GlobalProfile;
import egl.client.service.FxmlService;
import egl.client.service.model.profile.GlobalCredentialsService;
import egl.client.service.model.profile.GlobalProfileService;
import org.springframework.stereotype.Component;

@Component
public class GlobalProfileController extends ProfileSelectController<GlobalProfile> {

    private final GlobalCredentialsService globalCredentialsService;

    public GlobalProfileController(
            FxmlService fxmlService,
            GlobalProfileService globalProfileService,
            GlobalCredentialsService globalCredentialsService
            ) {
        super(fxmlService, globalProfileService);
        this.globalCredentialsService = globalCredentialsService;
    }

    @Override
    protected GlobalProfile createProfile() {
        return new GlobalProfile();
    }

    @Override
    protected void onEdit(GlobalProfile globalProfile, boolean isCreated, String title) {
        var globalCredentials = (isCreated
            ? new GlobalCredentials(globalProfile)
            : globalCredentialsService.findBy(globalProfile)
        );

        // TODO check errors
        if (null == globalCredentials) {
            return;
        }

        var changed = fxmlService.showInfoDialog(
                CredentialsInfoController.class,
                globalCredentials,
                title, isCreated
        );

        if (changed) {
            globalCredentialsService.save(globalCredentials);
            onSelect(globalProfile);
        }
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {

    }

    @Override
    public void prepareToShow() {

    }
}
