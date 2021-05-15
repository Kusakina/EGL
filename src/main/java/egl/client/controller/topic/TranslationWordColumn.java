package egl.client.controller.topic;

import egl.client.model.topic.category.Language;
import egl.client.model.topic.category.Translation;
import egl.client.model.topic.category.Word;
import egl.client.view.table.column.PropertyColumn;
import javafx.beans.NamedArg;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

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

    public TranslationWordColumn(@NamedArg("text") String title, @NamedArg("property") String propertyName, @NamedArg("language") Language language) {
        super(title, propertyName);

        var wordConverter = createWordConverter(language);
        setCellFactory(column -> new TextFieldTableCell<>(wordConverter));
    }
}
