package egl.client.view.table.topic;

import egl.client.model.topic.LocalTopic;
import egl.client.view.table.ButtonColumn;
import egl.client.view.table.DescribedEntitiesListView;
import javafx.fxml.FXML;

import java.util.function.Consumer;

public class LocalTopicsListView<T extends LocalTopic> extends DescribedEntitiesListView<T> {

    @FXML private ButtonColumn<T> editColumn;
    @FXML private ButtonColumn<T> removeColumn;

    public LocalTopicsListView() {
        loadView(LocalTopicsListView.class);
    }

    public void setOnEdit(Consumer<T> onEdit) {
        editColumn.setOnAction(onEdit);
    }

    public void setOnRemove(Consumer<T> onRemove) {
        removeColumn.setOnAction(onRemove);
    }
}
