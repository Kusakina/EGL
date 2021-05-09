package egl.client.controller;

import egl.client.service.FxmlService;
import egl.client.service.model.DescribedDataService;
import egl.core.model.DescribedData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public abstract class ListTableController<
        T extends DescribedData,
        ServiceType extends DescribedDataService<? extends T>
        > implements Controller {

    protected final FxmlService fxmlService;
    protected final ServiceType service;

    @FXML protected TableView<T> listTableView;
    @FXML protected TableColumn<T, String> nameColumn;
    @FXML protected TableColumn<T, Void> selectColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumns();
    }

    protected abstract void onSelect(ActionEvent event, T entity);

    private void initializeColumns() {
        ControllerUtils.initializePropertyColumn(nameColumn, "name");
        ControllerUtils.initializeButtonColumn(selectColumn, "Выбрать", this::onSelect);
    }

    @Override
    public void rescaleViews(double parentWidth, double parentHeight) {
        listTableView.setPrefSize(parentWidth, parentHeight);
        ControllerUtils.rescaleTableView(listTableView);
    }

    @Override
    public void prepareToShow() {
        var tableEntities = listTableView.getItems();
        tableEntities.clear();

        var entities = service.findAll();
        tableEntities.addAll(entities);
    }

    @Override
    public void prepareToClose() {

    }
}
