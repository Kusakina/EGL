package egl.client.controller.topic;

import egl.client.controller.info.AbstractEntityInfoController;
import egl.client.controller.topic.info.TheoryTextArea;
import egl.client.controller.topic.info.TranslationsEditableListView;
import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicType;
import egl.client.model.local.topic.category.Category;
import egl.client.model.local.topic.category.Translation;
import egl.client.view.info.FieldInfoView;
import egl.client.view.info.NameDescriptionInfoView;
import javafx.fxml.FXML;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class CategoryInfoController extends AbstractEntityInfoController<Category> {

    @FXML private NameDescriptionInfoView<Topic> nameDescriptionInfoView;
    @FXML private TheoryTextArea theoryTextArea;
    @FXML private TranslationsEditableListView translationsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addInnerInfoViews(
                new FieldInfoView<>(nameDescriptionInfoView, category -> category.getLocalTopicInfo().getTopic()),
                translationsListView,
                new FieldInfoView<>(theoryTextArea, category -> category.getLocalTopicInfo().getTheory())
        );
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        nameDescriptionInfoView.setPrefSize(parentWidth, parentHeight);

        translationsListView.setPrefSize(parentWidth, parentHeight);
        theoryTextArea.setPrefSize(parentWidth, parentHeight);
    }

    public void onAddTranslation() {
        translationsListView.getItems().add(new Translation());
    }

    @Override
    public void fillData() {
        super.fillData();

        entity.getLocalTopicInfo().setTopicHashCode(
                entity.getTopicHashCode()
        );

        if (isCreated) {
            entity.getLocalTopicInfo().getTopic().setTopicType(TopicType.CATEGORY);
        }
    }
}
