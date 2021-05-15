package egl.client.controller.topic;

import egl.client.controller.info.AbstractEntityInfoController;
import egl.client.controller.topic.info.TheoryTextArea;
import egl.client.controller.topic.info.TranslationsListView;
import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Translation;
import egl.client.view.info.NameDescriptionInfoView;
import javafx.event.ActionEvent;
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
    @FXML private TheoryTextArea theoryTextArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addInnerInfoViews(nameDescriptionInfoView, translationsListView, theoryTextArea);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        nameDescriptionInfoView.setPrefSize(parentWidth, parentHeight);
        translationsListView.setPrefSize(parentWidth, parentHeight);
        theoryTextArea.setPrefSize(parentWidth, parentHeight);
    }

    public void onAddTranslation(ActionEvent actionEvent) {
        translationsListView.getItems().add(new Translation());
    }
}
