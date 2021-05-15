package egl.client.controller.topic;

import egl.client.service.FxmlService;

public class TranslationsEditableListView extends TranslationsListView {

    public TranslationsEditableListView() {
        FxmlService.loadView(this, TranslationsEditableListView.class);
    }
}
