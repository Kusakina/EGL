package egl.client.controller.topic;

import egl.client.controller.info.AbstractEntityInfoController;
import egl.client.model.profile.LocalProfile;
import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Translation;
import egl.client.service.model.topic.CategoryService;
import egl.client.view.info.NameDescriptionInfoView;
import egl.client.view.table.list.CustomListView;
import javafx.fxml.FXML;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView
@RequiredArgsConstructor
public class CategoryInfoController extends AbstractEntityInfoController<Category> {

    @FXML private NameDescriptionInfoView<Category> nameDescriptionInfoView;
    @FXML private TranslationsListView translationsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void validateData() {
        nameDescriptionInfoView.validateData();
        translationsListView.validateData();
    }

    @Override
    public void fillData() {
        nameDescriptionInfoView.fillData(entity);
        translationsListView.fillData(entity);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        nameDescriptionInfoView.setPrefSize(parentWidth, parentHeight);
        translationsListView.setPrefSize(parentWidth, parentHeight);
    }

    @Override
    public void prepareToShow() {
        nameDescriptionInfoView.initData(entity, isCreated);
        translationsListView.initData(entity, isCreated);
    }

    @Override
    public void prepareToClose() {

    }
}
