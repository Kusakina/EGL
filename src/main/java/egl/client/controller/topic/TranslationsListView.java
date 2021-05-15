package egl.client.controller.topic;

import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Translation;
import egl.client.service.FxmlService;
import egl.client.view.info.EntityInfoView;
import egl.client.view.table.list.CustomListView;

import java.util.ArrayList;
import java.util.stream.Stream;

public class TranslationsListView extends CustomListView<Translation> implements EntityInfoView<Category>  {

    public TranslationsListView() {
        FxmlService.loadView(this, TranslationsListView.class);
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
        var translations = new ArrayList<>(getItems());
        category.setTranslations(translations);
    }
}
