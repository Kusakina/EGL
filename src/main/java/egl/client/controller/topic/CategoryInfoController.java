package egl.client.controller.topic;

import egl.client.controller.info.AbstractEntityInfoController;
import egl.client.controller.topic.info.TheoryTextArea;
import egl.client.controller.topic.info.TranslationsEditableListView;
import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Translation;
import egl.client.view.info.NameDescriptionInfoView;
import egl.client.view.pane.CustomBorderPane;
import javafx.fxml.FXML;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class CategoryInfoController extends AbstractEntityInfoController<Category> {

    @FXML private NameDescriptionInfoView<Category> nameDescriptionInfoView;
    @FXML private TheoryTextArea theoryTextArea;

    @FXML private CustomBorderPane editableTranslationsPane;
    @FXML private TranslationsEditableListView translationsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addInnerInfoViews(nameDescriptionInfoView, translationsListView, theoryTextArea);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        nameDescriptionInfoView.setPrefSize(parentWidth, parentHeight);

        if (!isCreated) {
            translationsListView.getColumns().remove(
                    translationsListView.getRemoveColumn()
            );

            editableTranslationsPane.setBottom(null);
        }

        translationsListView.setPrefSize(parentWidth, parentHeight);
        theoryTextArea.setPrefSize(parentWidth, parentHeight);
    }

    public void onAddTranslation() {
        translationsListView.getItems().add(new Translation());
    }
}
