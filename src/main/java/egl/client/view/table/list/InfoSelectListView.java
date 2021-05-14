package egl.client.view.table.list;

import egl.client.service.FxmlService;
import egl.core.model.DescribedEntity;

public class InfoSelectListView<T extends DescribedEntity> extends CustomListView<T> {

    public InfoSelectListView() {
        FxmlService.loadView(this, InfoSelectListView.class);
    }
}
