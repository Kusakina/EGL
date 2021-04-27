package egl.client.model.topic.category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Language {

    RUSSIAN("Русский"), ENGLISH("Английский");

    private final String localizedName;
}
