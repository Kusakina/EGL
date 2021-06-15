package egl.client.controller.topic.info;

import egl.client.model.local.topic.category.Language;
import egl.client.model.local.topic.category.Translation;
import egl.client.model.local.topic.category.Word;
import egl.client.view.table.column.PropertyColumn;
import javafx.beans.NamedArg;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import lombok.Setter;

import java.util.function.BiConsumer;

public class TranslationWordColumn extends PropertyColumn<Translation, Word> {

    private static StringConverter<Word> createWordConverter(Language language) {
        return new StringConverter<>() {
            @Override
            public String toString(Word word) {
                return (null == word ? "" : word.getText());
            }

            @Override
            public Word fromString(String s) {
                return new Word(s, language);
            }
        };
    }

    @Setter private BiConsumer<Translation, Word> wordProperty;

    public TranslationWordColumn(@NamedArg("text") String title, @NamedArg("property") String propertyName, @NamedArg("language") Language language) {
        super(title, propertyName);

        var wordConverter = createWordConverter(language);
        setCellFactory(TextFieldTableCell.forTableColumn(wordConverter));

        setOnEditCommit(event -> {
            var translations =  event.getTableView().getItems();
            var translation = translations.get(event.getTablePosition().getRow());
            wordProperty.accept(translation, event.getNewValue());
        });
    }
}
