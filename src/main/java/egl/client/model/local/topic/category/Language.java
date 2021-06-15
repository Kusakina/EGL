package egl.client.model.local.topic.category;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Language {

    RUSSIAN("Русский", 'а', 'я'),
    ENGLISH("Английский", 'a', 'z');

    private final String localizedName;
    private final char startLetter, endLetter;

    public int getLettersCount() {
        return endLetter - startLetter + 1;
    }

    public boolean contains(char ch) {
        ch = Character.toLowerCase(ch);
        return startLetter <= ch && ch <= endLetter;
    }

    public static Language of(String word) {
        char firstLetter = word.charAt(0);

        return Arrays.stream(values())
                .filter(language -> language.contains(firstLetter))
                .findAny()
                .orElse(ENGLISH);
    }
}
