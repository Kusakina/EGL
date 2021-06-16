package egl.client.controller.topic.info;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import egl.client.model.local.topic.category.Category;
import egl.client.model.local.topic.category.Translation;
import egl.client.service.FxmlService;
import egl.client.view.info.EntityInfoView;
import egl.client.view.table.list.CustomListView;
import javafx.fxml.FXML;

public class TranslationsListView extends CustomListView<Translation> implements EntityInfoView<Category>  {

    @FXML private TranslationWordColumn sourceColumn;
    @FXML private TranslationWordColumn targetColumn;

    public TranslationsListView() {
        FxmlService.loadView(this, TranslationsListView.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        sourceColumn.setWordProperty(Translation::setSource);
        targetColumn.setWordProperty(Translation::setTarget);
    }

    @Override
    public void initData(Category category, boolean isCreated) {
        setEditable(isCreated);
        setItems(category.getTranslations());
    }

    @Override
    public void validateData() {
        for (Translation translation : getItems()) {
            boolean invalid = Stream.of(translation.getSource(), translation.getTarget())
                    .anyMatch(word -> null == word || word.getText().isBlank());

            if (invalid) {
                throw new IllegalArgumentException("Слово не может быть пустым");
            }
        }
    }

    @Override
    public void fillData(Category category) {
        var translations = getItems();

        translations.forEach(translation -> {
            translation.getSource().updateLanguage();
            translation.getTarget().updateLanguage();
        });

        category.setTranslations(new HashSet<>(
                translations
        ));
    }
}
