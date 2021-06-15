package egl.client.view.table.list;

import egl.client.service.FxmlService;
import egl.client.model.core.DescribedEntity;

public class InfoSelectListView<T extends DescribedEntity> extends CustomListView<T> {

    public InfoSelectListView() {
        FxmlService.loadView(this, InfoSelectListView.class);
    }
}
