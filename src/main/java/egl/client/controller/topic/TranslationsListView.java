package egl.client.controller.topic;

import egl.client.model.topic.category.Translation;
import egl.client.service.FxmlService;
import egl.client.view.table.list.CustomListView;

public class TranslationsListView extends CustomListView<Translation> {

    public TranslationsListView() {
        FxmlService.loadView(this, TranslationsListView.class);
    }
}
